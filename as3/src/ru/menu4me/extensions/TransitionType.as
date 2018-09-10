package ru.menu4me.extensions {
	
	/** Represents a transition type based on Google Geofencing Transition Types */
	public class TransitionType {
		
		/** React when user is entering an geofence */
		public static const ENTER:int = 1;
		
		/** React when user is exiting an geofence */
		public static const EXIT:int = 2;
		
		/** React when user is inside a geofence for a while (based on a loiteringDelay of a Geofence) */
		public static const DWELL:int = 4;
	}
}