# GridImageSearch
Uses Google Image Search API to create a grid of filtered images.

Time spent: 12 hours

Completed user stories:

 * [x] Required: User can enter a search query that will display a grid of image results from the Google Image API.
 * [x] Required: User can click on "settings" which allows selection of advanced search options to filter results
 * [x] Required: User can configure advanced search filters such as: size, color, type, site
 * [x] Required: Subsequent searches will have any filters applied to the search results
 * [x] Required: User can tap on any image in results to see the image full-screen
 * [x] Required: User can scroll down “infinitely” to continue loading more image results (up to 8 pages)
 * [x] Advanced Optional: Check if internet is available
 * [x] Bonus Optional: Integrate StaggeredGridView (from f-barth)
 * [x] Advanced Optional: Implement TouchImageView (from MikeOrtiz)


Notes:

There were some issues with the staggered grid view. There were a bug that made one column
disappear if you scrolled too quickly. Another that hides images until you scrolled way past them. 

You can see a video of the app here: https://www.youtube.com/watch?v=B5VbasXcKjc&feature=youtu.be
