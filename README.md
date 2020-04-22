# WebCrawler

This is a CLI which returns the sitemap of a website given a domain name.\
the result is stored in a file named _sitemap.txt_ at the root of the project.\
the program takes up to two arguments; the first is the seed and the second is the depth of the crawling. If the latter is not specified, we continue the crawling until all URLs are visited.

## Pre-requisites

* Scala 2.12
* sbt 1.3.0
* Java 8

## Project Tree Structure

    ├── main

    │   └── scala

    │       ├── HttpConnector.scala

    │       ├── Parser.scala

    │       ├── WebCrawler.scala

    │       ├── WebCrawlerEngine.scala

    │       └── Writer.scala

    └── test

        ├── resources

        │   └── pageSource.txt

        └── scala

            ├── ParserTest.scala

            └── WebCrawlerEngineTest.scala

## Getting Started

### Step 1

_cd_ into  _WebCrawler_

### Step 2

run _./scripts/crawler.sh domainName depth_\
Example\
_run ./scripts/crawler.sh <https://salwa.dev> 5_

## Running The Tests

Type _sbt_ in the command line.
run the task _test_ in the interactive sbt console.

## Built With

* [Scala](https://www.scala-lang.org/)
* [sbt](https://www.scala-sbt.org/)

## Authors

* **Salwa Fathallah**
