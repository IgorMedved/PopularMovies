# PopularMovies
Project 1: Popular Movies App

Popular movies app completed

according to the following specification

User Interface - Layout

Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.

UI contains an element (i.e a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.

UI contains a screen for displaying the details for a selected movie.

Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.

User Interface - Function

When a user changes the sort criteria (“most popular and highest rated”) the main view gets updated correctly.

When a movie poster thumbnail is selected, the movie details screen is launched.

Network API Implementation
In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.


To make app run authorization is needed for accessing information on The Movie DataBase website:
to make it work add
app/src/main/java/com/example/popularmovies/ApiAuthorization.java
public class ApiAuthorization
{
    public static final String API_KEY = "YOUR API_KEY";
}

