# cd-collection
Basic web app to learn some Angular, ScalaJS and Http4s. Uses Scala, Sqlest and H2 db for backend.

#To-do - features

- Album delete feature
- Add album with new artist feature
- Add album from artist page feature
- Go to albums from artist page feature
- Album generator page
- Album sides functionality
- Artist linking functionality

#To-do - code/bugs
- Re-write JS with ScalaJS
- More intelligent filtering
- Get title bar items to highlight
- Factor out common page code (HMTL and JS)
- Angular dependencies
- Handle service errors in the UI - generally handle errors better and make Scala more idiomatic
- Formatting of the edit/add windows (clearable, mandatory, sizing)
- Weird bug where you edit an album, then go to the add window, and it blanks out details in the list
- Handling of special characters
- Table width issues
- Client side validation - icons to say content is valid
- Better text box for selecting artist on new album window

#To run

From SBT:

~re-start 

to fire up server, then go to localhost:8080