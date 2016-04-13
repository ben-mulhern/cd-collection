# cd-collection
Basic web app to learn some Angular, ScalaJS and Http4s. Uses Scala, Sqlest and H2 db for backend.

#To-do
1) Re-write JS with ScalaJS	

2) Finish the other pages & services (albums)

3) Figure out how all the Angular dependencies work - do I need to check them into my repo or can I get SBT to handle it?

4) Handle window forms better (well, at all) - validation errors, closing when complete

5) Add validation for artists to only allow one level of parenthood

6) Get serialisation working for responses

7) Get responses properly implemented in create method

#To run
Currently you have to manually create the tables in the H2 db (see SQL source for details)

To run from SBT:

~re-start 

to fire up server, then go to localhost:8080