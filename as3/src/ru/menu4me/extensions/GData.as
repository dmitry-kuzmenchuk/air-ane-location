package ru.menu4me.extensions {

	/** Represents a bundle of data of a geofence */
	public class GData {
		
		/** One hour in milliseconds = 3600000 */
		public static const OneHour:Number = 3600000;
		/** Six hours in milliseconds = 21600000 */
		public static const SixHours:Number = 21600000;
		/** Half of a day in milliseconds = 43200000 */
		public static const HalfDay:Number = 43200000;
		/** One day in milliseconds = 86400000 */
		public static const OneDay:Number = 86400000;
		/** TEST PURPOSE ONLY: one minute in milliseconds = 60000 */
		public static const TestMinute:Number = 60000;
		/** TEST PURPOSE ONLY: ten minutes in milliseconds = 600000 */
		public static const TestTenMinutes:Number = 600000;
		
		/**
		 * Unique name of geofence.
		 * Use only for better navigation between other geofences.
		 * Do not necessary for actual geofence
		 */
		public var title:String;
		/** Geofence ID (as int) */
		public var id:Number;
		/** Position: Geofence latitude (as double) */
		public var latitude:Number;
		/** Position: Geofence longitude (as double) */
		public var longitude:Number;
		/** Geofence radius (as float in meters) */
		public var radius:Number;
		
		/**
		 * Geofence data that is used in the extension to create an actual geofence and pass it into Geofencing Service
		 * @param	title 		Unique name of geofence. Do not necessary for actual geofence, used only for better navigation between the multiple geofences (as string)
		 * @param	id Unique 	Identeficator of geofence (as int)
		 * @param	latitude 	Position: geofence latitude (as double)
		 * @param	longitude 	Position: geofence longitude (as double)
		 * @param	radius 		Geofence radius (as float in meters)
		 */
		public function GData(title:String="", id:Number=0, latitude:Number=0, longitude:Number=0, radius:Number=1000) {
			this.title = title;
			this.id = id;
			this.latitude = latitude;
			this.longitude = longitude;
			this.radius = radius;
		}
	}
}