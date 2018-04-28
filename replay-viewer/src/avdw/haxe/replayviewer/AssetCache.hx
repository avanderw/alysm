package avdw.haxe.replayviewer;
import flash.display.BitmapData;
import openfl.Assets;
import openfl.text.Font;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class AssetCache 
{
	static public var font:Font;
	static public var statusScore:BitmapData;
	static public var statusEnergy:BitmapData;
	static public var statusHealth:BitmapData;
	static public var statusDamaage:BitmapData;

	public function new() 
	{
		font = Assets.getFont("font/perfect-DOS-VGA-437.ttf");
		
		statusScore = Assets.getBitmapData("img/status-score.png");
		statusEnergy = Assets.getBitmapData("img/status-energy.png");
		statusHealth = Assets.getBitmapData("img/16x-status-health.png");
		statusDamaage = Assets.getBitmapData("img/status-damage.png");
	}
	
}