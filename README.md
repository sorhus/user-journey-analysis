### User Journey Analysis

Parse web logs and identify user journeys on Hadoop.

Given a log where each entry contains
* a timestamp,
* a url,
* a user identifier,
this app will analyse the user journeys and construct the graph of all journeys.

The result is two files
* a list of the unique nodes in the graph with number of visits
* a list of all the edges in the graph

These files are easily combined to produce a [dot file](https://en.wikipedia.org/wiki/DOT_%28graph_description_language%29), 
assuming that the resulting graph is small enough to be kept in memory.

To test the app on a laptop we need to 
* build the app
    * `mvn clean package`
* generate a fake web log iwth 1000 user journeys.
    * `scala src/main/resources/generate_data.scala 1000 > target/data`
* run the app in local mode
    * `java -cp target/uja-0.1.0-SNAPSHOT-jar-with-dependencies.jar com.twitter.scalding.Tool com.github.sorhus.uja.UserJourneyAnalysis --local --input target/data --output-nodes target/nodes --output-edges target/edges`
* combine the output into a dot file and generate the graph
    * `./src/main/resources/generate_graph.sh`
* open target/uja.svg in your favorite browser

The end result will look something like this

![1000 users landed on www.example.com. Of those, 514 went on to www.example.com/a, etc](example-uja.png)