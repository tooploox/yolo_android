package com.tooploox.pokerml.data.tensorflowprocessing

import android.graphics.Bitmap
import com.tooploox.pokerml.domain.entity.Card
import com.tooploox.pokerml.domain.entity.Detection
import com.tooploox.pokerml.domain.entity.Rank
import com.tooploox.pokerml.domain.entity.Suit
import com.tooploox.pokerml.domain.gateway.PokerImageProcessor
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer

class TensorflowImageProcessor(
        model: MappedByteBuffer
) : PokerImageProcessor {

    companion object {
        const val DETECTION_THRESHOLD = 0.6
        const val NUM_BOXES_PER_BLOCK = 5
        const val NUM_CLASSES = 52
        private const val IMAGE_MEAN = 128
        private const val IMAGE_STD = 128.0f

        private val ANCHORS = doubleArrayOf(1.08, 1.19, 3.42, 4.41, 6.63, 11.38, 9.42, 5.11, 16.62, 10.52)
    }

    private val tensorflow = Interpreter(model)
    private var imgData: ByteBuffer = ByteBuffer.allocateDirect(
            1       // DIM_BATCH_SIZE
                    * 416   // Input image width
                    * 416   // Input image height
                    * 3     // Pixel size
                    * 4)    // Bytes per channel
    private val intValues = IntArray(416 * 416)

    private val output: Array<Array<Array<FloatArray>>> = Array(1) { Array(13) { Array(13) { FloatArray(285) } } }

    init {
        imgData.order(ByteOrder.nativeOrder())
    }

    override fun process(bitmap: Bitmap): List<Detection> {
        convertBitmapToByteBuffer(bitmap)
        tensorflow.run(imgData, output)
        return processOutput(output).map { Detection(cardFromClassIndex(it.key), it.value) }
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap) {
        imgData.rewind()
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 416, 416, true)
        resizedBitmap.getPixels(intValues, 0, resizedBitmap.width, 0, 0, resizedBitmap.width, resizedBitmap.height)
        // Convert the image to floating point.
        var pixel = 0
        for (i in 0 until 416) {
            for (j in 0 until 416) {
                val `val` = intValues[pixel++]
                addPixelValue(`val`)
            }
        }
    }

    private fun addPixelValue(pixelValue: Int) {
        imgData.putFloat(((pixelValue shr 16 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
        imgData.putFloat(((pixelValue shr 8 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
        imgData.putFloat(((pixelValue and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
    }

    private fun cardFromClassIndex(idx: Int) = Card(Rank.values()[idx % 13], Suit.values()[Math.floor(idx / 13.0).toInt()])

    private fun Array<FloatArray>.flatten(): List<Float> {
        val retList = mutableListOf<Float>()
        this.forEach {
            retList.addAll(it.asIterable())
        }
        return retList
    }

    private fun processOutput(data: Array<Array<Array<FloatArray>>>): Map<Int, Float> {
        val output = data[0] //output is now 13x13x285
        val flatOutput = mutableListOf<Float>()
        output.forEach { flatOutput.addAll(it.flatten()) }

        val resultsMap = mutableMapOf<Int, Float>()

        val gridHeight = 13
        val gridWidth = 13

        for (y in 0 until gridHeight) {
            for (x in 0 until gridWidth) {
                for (b in 0 until NUM_BOXES_PER_BLOCK) {
                    val offset = (gridWidth * (NUM_BOXES_PER_BLOCK * (NUM_CLASSES + 5)) * y
                            + NUM_BOXES_PER_BLOCK * (NUM_CLASSES + 5) * x
                            + (NUM_CLASSES + 5) * b)
//                    val blockSize = 32f  // bounding rect calculation left for posterity
//
//                    val xPos = (x + sigmoid(flatOutput[offset + 0])) * blockSize
//                    val yPos = (y + sigmoid(flatOutput[offset + 1])) * blockSize
//
//                    val w = (Math.exp(flatOutput[offset + 2].toDouble()) * ANCHORS[2 * b + 0]).toFloat() * blockSize
//                    val h = (Math.exp(flatOutput[offset + 3].toDouble()) * ANCHORS[2 * b + 1]).toFloat() * blockSize
//
//                    val rect = RectF(
//                            Math.max(0f, xPos - w / 2),
//                            Math.max(0f, yPos - h / 2),
//                            Math.min((416 - 1).toFloat(), xPos + w / 2),
//                            Math.min((416 - 1).toFloat(), yPos + h / 2))
                    val confidence = sigmoid(flatOutput[offset + 4])

                    var detectedClass = -1
                    var maxClass = 0f

                    val classes = FloatArray(NUM_CLASSES)
                    for (c in 0 until NUM_CLASSES) {
                        classes[c] = flatOutput[offset + 5 + c]
                    }
                    softmax(classes)

                    for (c in 0 until NUM_CLASSES) {
                        if (classes[c] > maxClass) {
                            detectedClass = c
                            maxClass = classes[c]
                        }
                    }

                    val confidenceInClass = maxClass * confidence
                    if (confidenceInClass > DETECTION_THRESHOLD) {
                        resultsMap.put(detectedClass, confidenceInClass)
                    }
                }
            }
        }
        return resultsMap
    }

    private fun sigmoid(x: Float): Float {
        return (1.0 / (1.0 + Math.exp((-x).toDouble()))).toFloat()
    }

    private fun softmax(floats: FloatArray) {
        var max = java.lang.Float.NEGATIVE_INFINITY
        for (number in floats) {
            max = Math.max(max, number)
        }
        var sum = 0.0f
        for (i in floats.indices) {
            floats[i] = Math.exp((floats[i] - max).toDouble()).toFloat()
            sum += floats[i]
        }
        for (i in floats.indices) {
            floats[i] = floats[i] / sum
        }
    }
}
