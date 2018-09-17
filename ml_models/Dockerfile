FROM nvidia/cuda:9.0-cudnn7-devel-ubuntu16.04

RUN \
	apt-get update && apt-get install -y \
	vim \
	autoconf \
        automake \
	libtool \
	build-essential \
	git \
	wget

RUN git clone https://github.com/pjreddie/darknet.git

RUN mkdir /balrog_mnt

WORKDIR darknet/

COPY ./Makefile .

RUN make

RUN wget https://pjreddie.com/media/files/yolov3-tiny.weights
