# cd-collection
Basic web app to learn some Angular, ScalaJS and Http4s. Uses Scala, Sqlest and H2 db for backend.

#To-do
- Re-write JS with ScalaJS (and re-factor!)

- Album page - finish it.
	- Edit/delete/display.
	- Make filter more intelligent.
	- Get title bar item to highlight.
	- Default value in Album type select
	- Get datalist/model working
	- Placeholders for form fields?

- Generator page

- Figure out how all the Angular dependencies work - do I need to check them into my repo or can I get SBT to handle it?

- Handle service errors in the UI - generally handle errors better and make Scala more idiomatic

- Formatting of the edit/add windows (clearable, mandatory, sizing)

- Table column widths - keep them fixed width somehow?

- Add all the parent artist logic and functionality inc domain validation for artists to only allow one level of parenthood

- Ship master copy with empty DB (fix this after adding new columns)

- Add URI for albums within an artists e.g. 
	/artists/{artist-id}/albums/{album-id}
	/artists/{artist-id}/albums?searchTerm=Metal

- Factor out common HTML for all pages

- Searching over special characters

- Album sides functionality

#To run

From SBT:

~re-start 

to fire up server, then go to localhost:8080