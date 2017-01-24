#!/bin/bash

clear; gradle clean build && java -jar build/libs/microservices.jar
