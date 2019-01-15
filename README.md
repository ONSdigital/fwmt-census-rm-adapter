[![Build Status](https://travis-ci.org/ONSdigital/fwmt-rm-adapter.svg?branch=master)](https://travis-ci.org/ONSdigital/fwmt-rm-adapter) [![codecov](https://codecov.io/gh/ONSdigital/fwmt-rm-adapter/branch/master/graph/badge.svg)](https://codecov.io/gh/ONSdigital/fwmt-rm-adapter) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/9a61e9e47fef456894559330ba96b82c)](https://www.codacy.com/app/ONSDigital_FWMT/fwmt-census-rm-adapter?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ONSdigital/fwmt-census-rm-adapter&amp;utm_campaign=Badge_Grade)

# fwmt-census-rm-adapter

This service is a gateway between the Response Management System and the FWMT job service.

It takes an Action Instruction (Request or Delete) and transforms it into a FWMT Common Object of the matching type, and then sends it to the Job Service. 
It also accepts a TM response message from the JobSvc, transforms it to XML and then sends it back to RM.

All communication with RM and the JobSvc is done via AMPQ queues using RabbitMQ.

Requires RabbitMQ to start:

	docker run --name rabbit -p 5671-5672:5671:5672 -p 15671-15672:15671-15672 -d rabbitmq:3.6-management

To run:

	./gradlew bootRun

## rm-canonical mapping

![](/canonical-rm-mapping.png "canonical - rm - mapping")	
