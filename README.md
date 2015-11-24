# cd-collection
Basic web app to learn some Angular, JS and Vertx. Uses Scala, Sqlest and H2 db for backend.

#To-do
1) Route-matcher - Need to write my own because Vertx one only works with Scala 2.10, and Sqlest only works with 2.11

2) Refactor - Need to refactor all the services and the server class, serialisation etc

3) Finish the other pages (albums)

4) Auto-create the DB on first run

#To run
Currently you have to manually create the tables in the H2 db (see dal and ddl source for details)

To run from SBT:

sbt "run-main org.vertx.java.platform.impl.cli.Starter run scala:server.CdCollectionServer"