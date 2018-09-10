package screens {
	import feathers.controls.Button;
	import feathers.controls.PanelScreen;
	import feathers.controls.WebView;
	import ru.menu4me.extensions.GData;
	import ru.menu4me.extensions.Location;
	import starling.core.Starling;
	import starling.events.Event;
	
	import ru.menu4me.utils.Log;
	
	public class MenuScreen extends PanelScreen {
		private static const TAG			: String = "Menu Screen";
		private var addGeofencesButton		: Button = null;
		private var location				: Location = null;
		private var clientInfo				: String = "{\"os\":\"Adobe Windows\",\"deviceId\":\"71e4889df5fd26a0d792597556c5e7523b5d288ec79971371ca5a5f1f0623a7f\",\"appVersion\":\"3.0.19\",\"osVersion\":\"Windows 10\",\"brand\":\"\",\"appId\":\"com.menu4me.richiepersonal.sushifood\",\"model\":\"\"}";
		
		public function MenuScreen() {
			Log.d(TAG, "MenuScreen()");
			super();
		}	
		
		override protected function initialize():void {
			Log.d(TAG, "initialize()");
			super.initialize();
			title = "Location";
			
			addGeofencesButton = new Button();
			addGeofencesButton.label = "Add geofences";
			addGeofencesButton.x = 5;
			addGeofencesButton.y = 160;
			addGeofencesButton.addEventListener(Event.TRIGGERED, addGeofencesButtonTriggered);
			
			location = Location.getInstance();
			location.initData(clientInfo);
			location.registerUpdates();
			this.addChild(addGeofencesButton);
		}
		
		private function addGeofencesButtonTriggered(e:Event):void {
			Log.d(TAG, "addGeofencesButtonTriggered()");
			var geofences : Array = new Array();
			geofences.push(new GData("SomeShitPlace", 195, 53.557021, 49.216027));
			location.addGeofences(geofences);
		}
	}
}