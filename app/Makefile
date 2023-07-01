run-dist:
	./build/install/app/bin/app

help:
	./build/install/app/bin/app -h

compare:
	./build/install/app/bin/app /home/finch/java-project-71/app/src/main/resources/files/file1.json /home/finch/java-project-71/app/src/main/resources/files/file2.json

build:
	./gradlew clean build

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest

build-run: build run

.PHONY: build
