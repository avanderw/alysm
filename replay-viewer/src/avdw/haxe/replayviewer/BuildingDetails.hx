package avdw.haxe.replayviewer;

import flash.text.TextField;
import openfl.display.Bitmap;
import openfl.Assets;
import openfl.display.BitmapData;
import openfl.display.Sprite;
import openfl.text.Font;
import openfl.text.TextFieldAutoSize;
import openfl.text.TextFormat;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class BuildingDetails extends Sprite
{

	public function new(gameDetails:Dynamic)
	{
		super();
		trace(gameDetails);

		try
		{
			var padding = 2;
			var wdth:Int = 64 * 3 + 3 * 2 * padding;
			var hght:Int = 0;
			graphics.lineStyle(2);
			graphics.moveTo(0, hght); graphics.lineTo(wdth, hght);
			hght = 16 + 2 * padding;
			graphics.moveTo(0, hght); graphics.lineTo(wdth, hght);
			hght = 16 + 64 + 4 * padding;
			graphics.moveTo(0, hght); graphics.lineTo(wdth, hght);
			//hght = 16 + 64 + 16 + 6 * padding;
			//graphics.moveTo(0, hght); graphics.lineTo(wdth, hght);

			wdth = 0;
			graphics.moveTo(wdth, 0); graphics.lineTo(wdth, hght);
			wdth = 64 + 2 * padding;
			graphics.moveTo(wdth, 0); graphics.lineTo(wdth, hght);
			wdth = 64 + 64 + 4 * padding;
			graphics.moveTo(wdth, 0); graphics.lineTo(wdth, hght);
			wdth = 64 + 64 + 64 + 6 * padding;
			graphics.moveTo(wdth, 0); graphics.lineTo(wdth, hght);

			var format:TextFormat = new TextFormat(AssetCache.font.fontName, 16);

			var attackBuilding:BitmapData = Assets.getBitmapData("img/64x-building-attack.png");
			var defenseBuilding:BitmapData = Assets.getBitmapData("img/64x-building-defense.png");
			var energyBuilding:BitmapData = Assets.getBitmapData("img/64x-building-energy.png");
			var statusPrice:BitmapData = Assets.getBitmapData("img/16x-status-price.png");
			var statusHealth:BitmapData = Assets.getBitmapData("img/16x-status-health.png");

			var attackBuildingBmp:Bitmap = new Bitmap(attackBuilding);
			var defenseBuildingBmp:Bitmap = new Bitmap(defenseBuilding);
			var energyBuildingBmp:Bitmap = new Bitmap(energyBuilding);

			attackBuildingBmp.y = defenseBuildingBmp.y = energyBuildingBmp.y = 16 + 3 * padding;
			attackBuildingBmp.x = padding;
			defenseBuildingBmp.x = 64 + 3 * padding;
			energyBuildingBmp.x = 64 * 2 + 5 * padding;

			addChild(attackBuildingBmp);
			addChild(defenseBuildingBmp);
			addChild(energyBuildingBmp);

			var attackPriceBmp:Bitmap = new Bitmap(statusPrice);
			var defensePriceBmp:Bitmap = new Bitmap(statusPrice);
			var energyPriceBmp:Bitmap = new Bitmap(statusPrice);

			attackPriceBmp.y = defensePriceBmp.y =energyPriceBmp.y = padding;
			attackPriceBmp.x = 64 * 0 + 1 * padding;
			defensePriceBmp.x = 64 * 1 + 3 * padding;
			energyPriceBmp.x = 64 * 2 + 5 * padding;

			addChild(attackPriceBmp);
			addChild(defensePriceBmp);
			addChild(energyPriceBmp);

			var attackPriceText:TextField = textfield(gameDetails.buildingPrices.ATTACK, format);
			var defensePriceText:TextField = textfield(gameDetails.buildingPrices.DEFENSE, format);
			var energyPriceText:TextField = textfield(gameDetails.buildingPrices.ENERGY, format);

			attackPriceText.y = defensePriceText.y = energyPriceText.y = 0;
			attackPriceText.x = 64 * 1 + 2 * padding - attackPriceText.width;
			defensePriceText.x = 64 * 2 + 4 * padding - defensePriceText.width;
			energyPriceText.x = 64 * 3 + 6 * padding - energyPriceText.width;

			addChild(attackPriceText);
			addChild(defensePriceText);
			addChild(energyPriceText);

			/*
			var attackPriceBmp:Bitmap = new Bitmap(statusPrice);
			var attackPriceText:TextField = new TextField();
			attackPriceText.autoSize = TextFieldAutoSize.LEFT;
			attackPriceText.setTextFormat(textFormat);
			attackPriceText.text = gameDetails.buildingPrices.ATTACK;
			var attackHealthText:TextField = new TextField();
			attackHealthText.autoSize = TextFieldAutoSize.LEFT;
			attackHealthText.setTextFormat(textFormat);
			attackHealthText.text = "5";
			attackPriceBmp.y = attackHealthBmp.y = padding;
			attackBuildingBmp.x = attackBuildingBmp.y = padding;
			attackPriceText.x = 16-4;
			attackHealthBmp.x = 32+4;
			attackHealthText.x = 48 + 4;

			var defensePriceBmp:Bitmap = new Bitmap(statusPrice);
			var defensePriceText:TextField = new TextField();
			defensePriceText.autoSize = TextFieldAutoSize.LEFT;
			defensePriceText.setTextFormat(textFormat);
			defensePriceText.text = gameDetails.buildingPrices.DEFENSE;
			var defenseHealthText:TextField = new TextField();
			defenseHealthText.autoSize = TextFieldAutoSize.LEFT;
			defenseHealthText.setTextFormat(textFormat);
			defenseHealthText.text = "20";
			defenseBuildingBmp.x = 64 + 3 * padding;
			defensePriceBmp.y = defenseHealthBmp.y = padding;
			defensePriceText.x = 64 + 16 -4;
			defenseHealthBmp.x = 64 + 32;
			defenseHealthText.x = 64 + 48;

			attackBuildingBmp.y = statusBar.height + padding;
			defenseBuildingBmp.y = statusBar.height + padding;
			*/

		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	function textfield(value:String, format:TextFormat):TextField
	{
		var tf:TextField = new TextField();
		try {
			tf.autoSize = TextFieldAutoSize.LEFT;
			tf.setTextFormat(format);
			tf.text = ""+value;
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
		return tf;
	}

}