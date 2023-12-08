# GamesInfoApp
A simple Android application for displaying information about video games.

## Some design decisions made and their reasoning

### Persisting data

There is a Database interface which provides a layer of abstraction for persisting data, so clients don't know where the data is being saved and retrieved from. Clients call a Factory class which decides a concrete implementation class for the interface.
	
This is possibly not necesarry, as the app is small and works with minimal amount of data. However in case the project gets bigger and it will be necesarry to provide additional ways of working with this data, now it will be easier to do so. Instead of changing every client call we will only change the Factory implementation.
	
	
### Persisting data locally

This works by reading and writing data to/from a File created in application's internal directory. 
	
This approach was chosen because it seems appropriate and is easy and quick to do. Using a database for example would be too much as we only need small amount of basic data.
	
	
### First startup screens

SharedPreferences are used to store information if first startup has been completed or not. On every launch this flag is checked and from the main fragment a redirect is made to the first startup fragment if needed. After the first startup process is completed the fragment stack is cleared.
	
SharedPreferences makes sense here because it permanently stores simple key value pairs accessible from anywhere in the app.
Stack is cleared to make sure users can not go back to first startup screens.
	

### Constants file

To host constants which are used in multiple files.
	
By using this approach there can be no mispells by typing manually. If changing the key's value it is now necesarry to change in only one place.
	

### Helper file
	
To host public methods which are used in multiple files throughout the app.
	
Reduces code duplication and makes usage easier.
	

### Api models

The app does not use all of it's models. 
Every model used for working with the api has a name 'ModelNameApi'.
	
Models were auto generated using an online tool. This saved alot of development time, but generated all properties even thought not all are needed. Still they are left behind in case they will be needed. 
Having 'Api' in model name suggests that it is used for working with the api. This leaves an option of creating separate models with different and/or additional properties. This can all be done in one model, but perhaps this way it is a bit cleaner.
	

### Locking the app in portrait mode

This makes it much easier and faster than handling all visuals and state changes.
