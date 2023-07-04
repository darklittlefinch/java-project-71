run-dist:
	./build/install/app/bin/app

help:
	./build/install/app/bin/app -h

compare-json:
	./build/install/app/bin/app src/test/resources/files/file1.json src/test/resources/files/file2.json

compare-yaml-stylish:
	./build/install/app/bin/app -f stylish src/test/resources/files/file1.yml src/test/resources/files/file2.yml

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
