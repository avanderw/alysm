package avdw.haxe.replayviewer;

import flash.display.BitmapData;
import openfl.Assets;
import openfl.filters.ColorMatrixFilter;
import openfl.geom.Point;
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
	static public var upArrow:BitmapData;

	static public var buildingEmpty:BitmapData;
	static public var buildingAttack:BitmapData;
	static public var buildingDefense:BitmapData;
	static public var buildingEnergy:BitmapData;

	static public var missile:BitmapData;
	
	static public var darkA:UInt = 0x124FB2;
	static public var lightA:UInt = 0xBED6FF;
	static public var darkB:UInt = 0xB28019;
	static public var lightB:UInt = 0xFFF8EA;
	static public var darkWin:UInt = 0x00B22B;
	static public var lightWin:UInt = 0xBCFFCC;
	static public var darkLose:UInt = 0xB2000E;
	static public var lightLose:UInt = 0xFFBCC1;
	static public var darkEnergy:UInt = 0xB2880C;
	static public var lightEnergy:UInt = 0xFFF2CD;
	static public var darkDefend:UInt = 0x087F62;
	static public var lightDefend:UInt = 0xCCFFF2;
	static public var darkAttack:UInt = 0x9410B2;
	static public var lightAttack:UInt = 0xF7D3FF;

	var energyColor:ColorMatrixFilter = new ColorMatrixFilter([
				0, 0, 0, 0, 255,
				0, 0, 0, 0, 180,
				0, 0, 0, 0, 0,
				0, 0, 0, 1, 0
			]);
	var defenseColor:ColorMatrixFilter = new ColorMatrixFilter([
				0, 0, 0, 0, 20,
				0, 0, 0, 0, 204,
				0, 0, 0, 0, 138,
				0, 0, 0, 1, 0
			]);
	var attackColor:ColorMatrixFilter = new ColorMatrixFilter( [
				0, 0, 0, 0, 204,
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 255,
				0, 0, 0, 1, 0
			]);
	var missileColor:ColorMatrixFilter = new ColorMatrixFilter([
				0, 0, 0, 0, 178,
				0, 0, 0, 0, 18,
				0, 0, 0, 0, 20,
				0, 0, 0, 1, 0]);
	var healthColor:ColorMatrixFilter = new ColorMatrixFilter([
				0, 0, 0, 0, 178,
				0, 0, 0, 0, 18,
				0, 0, 0, 0, 20,
				0, 0, 0, 1, 0]);
	var neutralColor:ColorMatrixFilter = new ColorMatrixFilter([
				0, 0, 0, 0, 214,
				0, 0, 0, 0, 214,
				0, 0, 0, 0, 214,
				0, 0, 0, 1, 0
			]);
	var scoreColor:ColorMatrixFilter = new ColorMatrixFilter([
				0, 0, 0, 0, 255,
				0, 0, 0, 0, 155,
				0, 0, 0, 0, 0,
				0, 0, 0, 1, 0
			]);
	var cooldownColor:ColorMatrixFilter = new ColorMatrixFilter([
				0, 0, 0, 0, 9,
				0, 0, 0, 0, 178,
				0, 0, 0, 0, 68,
				0, 0, 0, 1, 0
			]);

	public function new()
	{
		font = Assets.getFont("font/perfect-DOS-VGA-437.ttf");

		statusScore = Assets.getBitmapData("img/status-score.png");
		statusScore.applyFilter(statusScore, statusScore.rect, new Point(), scoreColor);
		statusEnergy = Assets.getBitmapData("img/status-energy.png");
		statusEnergy.applyFilter(statusEnergy, statusEnergy.rect, new Point(), energyColor);
		statusHealth = Assets.getBitmapData("img/16x-status-health.png");
		statusHealth.applyFilter(statusHealth, statusHealth.rect, new Point(), healthColor);
		statusDamaage = Assets.getBitmapData("img/status-damage.png");
		statusConstruction = Assets.getBitmapData("img/status-construction.png");
		statusConstruction.applyFilter(statusConstruction, statusConstruction.rect, new Point(), scoreColor);
		statusCooldownPeriod = Assets.getBitmapData("img/status-cooldown-period.png");
		statusCooldownPeriod.applyFilter(statusCooldownPeriod, statusCooldownPeriod.rect, new Point(), cooldownColor);
		statusPrice = Assets.getBitmapData("img/16x-status-price.png");
		statusSpeed = Assets.getBitmapData("img/status-speed.png");
		statusSpeed.applyFilter(statusSpeed, statusSpeed.rect, new Point(), energyColor);
		statusDestroyMultiplier = Assets.getBitmapData("img/status-destroy-multiplier.png");
		statusCooldownLeft = Assets.getBitmapData("img/status-cooldown-left.png");
		statusDamage = Assets.getBitmapData("img/status-damage.png");

		buildingEmpty = Assets.getBitmapData("img/64x-building-empty.png");
		buildingEmpty.applyFilter(buildingEmpty, buildingEmpty.rect, new Point(), neutralColor);
		buildingAttack = Assets.getBitmapData("img/64x-building-attack.png");
		buildingAttack.applyFilter(buildingAttack, buildingAttack.rect, new Point(), attackColor);
		buildingDefense = Assets.getBitmapData("img/64x-building-defense.png");
		buildingDefense.applyFilter(buildingDefense, buildingDefense.rect, new Point(), defenseColor);
		buildingEnergy = Assets.getBitmapData("img/64x-building-energy.png");
		buildingEnergy.applyFilter(buildingEnergy, buildingEnergy.rect, new Point(), energyColor);

		missile = Assets.getBitmapData("img/missile.png");
		missile.applyFilter(missile, missile.rect, new Point(), missileColor);
		
		upArrow = Assets.getBitmapData("img/up-arrow.png");
		upArrow.applyFilter(upArrow, upArrow.rect, new Point(), cooldownColor);
	}

}