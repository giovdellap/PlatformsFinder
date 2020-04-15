# PLATFORMSFINDER

An app that shows offshore platforms near you  
Works only in Italy because APIs are Italy-based  
A demo video is avalaible in media folder


## CODE EXPLANATION

Packages:  
- MODEL  
  - PlatformsDao  
  The DB Interface, contains method to ask DB by queries
  - PlatformsDB
  The real DB Instance
  - PlatformsTable
  DB table, contains all datas of the platform  


- TOOLS  
  - BundleFactory  
  A factory for bundles for every activity in the app  
  - DBHandler  
  Handles app connections with DB in a easy way  
  - JSONDecoder  
  Decodes API's JSON files into objects  
  - MyVolley  
  Handles Volley operations  
  - StringMaker  
  A factory for styled strings(SpannableStrings)  
  - URLManager  
  Manages URL completion for geocoding services  
  - VolleyListenersFactory  
  A factory for all Volley Listeners in the app  


- VIEW  
  - MainActivity  
  Launcher activity of the app, allows user to choose between localization and address mode and to choose distance  
  - MapActivity  
  Shows localization and platforms on a map, asks GPS permissions if needed  
  - ListActivity  
  Shows platforms as a CardView's list  
  - ListAdapter  
  Adapter and ViewHolder for ListActivity's RecyclerView  
  - DetailsActivity  
  Shows platform's details

## SCREENSHOTS

![MainActivity](/media/main.jpg)


![MapActivity](/media/map.jpg)


![DetailsActivity](/media/details.jpg)
