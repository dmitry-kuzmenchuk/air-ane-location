package ru.menu4me.utils {
	import Date;
	
	/** Represents android-like logging into FlashDevelop debug console */
	public class Log {
		private static var date:Date;
		
		/** Debug message */
		public static function d(tag:String, text:String):void {
			date = new Date();
			trace("[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "::" + date.getMilliseconds() + "] " + "(" + tag + ") " + text);
		}
		
		/** Warning message */
		public static function w(tag:String, text:String):void {
			date = new Date();
			trace("[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "::" + date.getMilliseconds() + "] " + "(" + tag + ") " + "[WARNING] " + text);
		}
		
		/** Error message */
		public static function e(tag:String, text:String):void {
			date = new Date();
			trace("[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "::" + date.getMilliseconds() + "] " + "(" + tag + ") " + "[ERROR] " + text);	
		}
	}
}