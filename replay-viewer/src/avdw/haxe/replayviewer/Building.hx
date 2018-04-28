package avdw.haxe.replayviewer;
import openfl.text.TextField;
import openfl.display.Bitmap;
import openfl.display.BitmapData;
import openfl.display.Sprite;
import openfl.text.TextFormat;
import openfl.text.TextFormatAlign;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class Building extends Sprite
{

	public function new(buildingData:Dynamic)
	{
		super();
		var bmp:Bitmap = new Bitmap();
		switch (buildingData.buildingType)
		{
			case "ATTACK": bmp.bitmapData = AssetCache.buildingAttack;
			case "DEFENSE": bmp.bitmapData = AssetCache.buildingDefense;
			case "ENERGY": bmp.bitmapData = AssetCache.buildingEnergy;
			default: bmp.bitmapData = AssetCache.buildingEmpty;
		}

		if (buildingData.playerType == "B")
		{
			bmp.x = bmp.width;
			bmp.scaleX = -1;
		}

		var statuses:Array<Sprite> = new Array();
		if (buildingData.constructionTimeLeft > 0) {statuses.push(createStatus(AssetCache.statusConstruction, buildingData.constructionTimeLeft));}
		if (buildingData.weaponCooldownPeriod > 0){statuses.push(createStatus(AssetCache.statusCooldownPeriod, buildingData.weaponCooldownPeriod));}
		if (buildingData.price > 0){statuses.push(createStatus(AssetCache.statusPrice, buildingData.price));}
		if (buildingData.weaponSpeed > 0){statuses.push(createStatus(AssetCache.statusSpeed, buildingData.weaponSpeed));}
		if (buildingData.destroyMultiplier > 0){statuses.push(createStatus(AssetCache.statusDestroyMultiplier, buildingData.destroyMultiplier));}
		if (buildingData.energyGeneratedPerTurn > 0){statuses.push(createStatus(AssetCache.statusEnergy, buildingData.energyGeneratedPerTurn));}
		if (buildingData.weaponCooldownTimeLeft > 0){statuses.push(createStatus(AssetCache.statusCooldownLeft, buildingData.weaponCooldownTimeLeft));}
		if (buildingData.weaponDamage > 0){statuses.push(createStatus(AssetCache.statusDamage, buildingData.weaponDamage));}
		if (buildingData.constructionScore > 0){statuses.push(createStatus(AssetCache.statusScore, buildingData.constructionScore));}		

		addChild(bmp);
		for (i in 0...statuses.length)
		{
			statuses[i].y = i * statuses[i].height;
			addChild(statuses[i]);
		}
	}

	var format:TextFormat;
	function createStatus(bmd:BitmapData, value:String):Sprite
	{
		var sprite:Sprite = new Sprite();
		try {
			if (format == null)
			{
				format = new TextFormat(AssetCache.font.fontName, 16);
				format.align = TextFormatAlign.LEFT;
			}

			var bmp:Bitmap = new Bitmap(bmd);
			var tf:TextField = new TextField();
			tf.setTextFormat(format);
			tf.text = ""+value;
			tf.height = tf.textHeight;
			tf.x = bmp.width;

			sprite.addChild(bmp);
			sprite.addChild(tf);
		}
		catch (e:Dynamic) {trace(e);}
		return sprite;
	}
}