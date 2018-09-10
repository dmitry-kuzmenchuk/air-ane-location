package ru.menu4me.extensions {
	import flash.external.ExtensionContext;
	import flash.events.EventDispatcher;
	import flash.events.Event;
	
	import ru.menu4me.extensions.GData;
	import ru.menu4me.extensions.TransitionType;
	import ru.menu4me.utils.Log;
	
	public class Location extends EventDispatcher {
		private static const TAG			: String = "Location";
		private static var extensionContext	: ExtensionContext = null;
		private static var instance			: Location = null;
		
		public function Location() {
			Log.d(TAG, "Location()");
			
			if (!instance) {
				if (!extensionContext) {
					extensionContext = ExtensionContext.createExtensionContext("ru.menu4me.extensions.Location", "");
				}
				
				if (!extensionContext) {
					Log.e(TAG, "Unable to create Extension Context");
				} else {
					Log.d(TAG, "Extension Context was created successfully");
				}
			} else {
				Log.w(TAG, "Do not use constructor directly, it's a singleton. Use getInstance() instead");
			}
		}
		
		/** Initialize and get instance of an object */
		public static function getInstance():Location {
			Log.d(TAG, "getInstance()");
			return instance ? instance : instance = new Location();
		}
		
		/** Used to unregister repeating alarm that send broadcast to the listener which perform some tasks */
		public function unregisterUpdates():void {
			Log.d(TAG, "unregisterUpdates()");
			extensionContext.call("unregisterUpdates");
		}
		
		/** Used to register repeating alarm that send broadcast to the listener which perform some tasks */
		public function registerUpdates():void {
			Log.d(TAG, "registerUpdates()");
			extensionContext.call("registerUpdates");
		}
		
		/** Force update of geofences list */
		public function sendUpdateIntent():void {
			Log.d(TAG, "sendUpdateIntent");
			extensionContext.call("sendUpdateIntent");
		}
		
		/** Init data, that necessary for proper work of an extension */
		public function initData(clientInfo:String):void {
			Log.d(TAG, "initData()");
			extensionContext.call("initData", clientInfo);
		}
		
		/**
		 * Used to initialize new geofences list with passed array of geofences
		 * @param	geofences 			Array of geofences (pass as GData)
		 * @param	expirationDuration 	Duration of life (pass in Ms)
		 * @param	transitionType 		Triggering type of geofences (pass as TransitionType)
		 */
		public function addGeofences(geofences:Array, expirationDuration:int=GData.SixHours, transitionType:int=TransitionType.ENTER):void {
			Log.d(TAG, "registerNewList()");
			extensionContext.call("registerNewList", expirationDuration);
			for (var i:int = 0; i < geofences.length; i++) {
				extensionContext.call("addGeofence", geofences[i].title, geofences[i].id, geofences[i].latitude, geofences[i].longitude, geofences[i].radius, expirationDuration, transitionType);
			}
			extensionContext.call("sendUpdateIntent");
		}
	}
}