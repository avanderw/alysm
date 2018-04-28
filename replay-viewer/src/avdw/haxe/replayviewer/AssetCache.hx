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
	static public var statusConstruction:BitmapData;
	static public var statusCooldownPeriod:BitmapData;
	static public var statusPrice:BitmapData;
	static public var statusSpeed:BitmapData;
	static public var statusDestroyMultiplier:BitmapData;
	static public var statusCooldownLeft:BitmapData;
	static public var statusDamage:BitmapData;
	
	static public var buildingEmpty:BitmapData;
	static public var buildingAttack:BitmapData;
	static public var buildingDefense:BitmapData;
	static public var buildingEnergy:BitmapData;

	public function new() 
	{
		font = Assets.getFont("font/perfect-DOS-VGA-437.ttf");
		
		statusScore = Assets.getBitmapData("img/status-score.png");
		statusEnergy = Assets.getBitmapData("img/status-energy.png");
		statusHealth = Assets.getBitmapData("img/16x-status-health.png");
		statusDamaage = Assets.getBitmapData("img/status-damage.png");
		statusConstruction = Assets.getBitmapData("img/status-construction.png");
		statusCooldownPeriod = Assets.getBitmapData("img/status-cooldown-period.png");
		statusPrice = Assets.getBitmapData("img/16x-status-price.png");
		statusSpeed = Assets.getBitmapData("img/status-speed.png");
		statusDestroyMultiplier = Assets.getBitmapData("img/status-destroy-multiplier.png");
		statusCooldownLeft = Assets.getBitmapData("img/status-cooldown-left.png");
		statusDamage = Assets.getBitmapData("img/status-damage.png");
		
		buildingEmpty = Assets.getBitmapData("img/64x-building-empty.png");
		buildingAttack = Assets.getBitmapData("img/64x-building-attack.png");
		buildingDefense = Assets.getBitmapData("img/64x-building-defense.png");
		buildingEnergy = Assets.getBitmapData("img/64x-building-energy.png");
	}
	
}