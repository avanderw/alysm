package avdw.haxe.replayviewer;
import flash.display.Bitmap;
import flash.display.Sprite;
import flash.text.TextField;
import openfl.text.TextFieldAutoSize;
import openfl.text.TextFormat;
import openfl.text.TextFormatAlign;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class PlayerDetails extends Sprite
{
	var playerA:PlayerSprite;
	var playerB:PlayerSprite;

	public function new()
	{
		super();

		try
		{
			playerA = new PlayerSprite("A", true);
			playerB = new PlayerSprite("B", false);

			var vsText:TextField = new TextField();
			vsText.setTextFormat(new TextFormat(AssetCache.font.fontName, 32));
			vsText.autoSize = TextFieldAutoSize.LEFT;
			vsText.text = "VS";

			vsText.y = playerA.y + (playerA.height - vsText.height) / 2;
			vsText.x = playerA.x + playerA.width;
			playerB.x = vsText.x + vsText.width;

			addChild(playerA);
			addChild(vsText);
			addChild(playerB);
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	public function update(players:Array<Dynamic>)
	{
		try
		{
			trace(players);
			for (i in 0...players.length)
			{
				if (players[i].playerType == "A")
				{
					playerA.update(players[i]);
				}
				else if (players[i].playerType == "B")
				{
					playerB.update(players[i]);
				}
			}
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

}

class PlayerSprite extends Sprite
{
	var _name:String;

	public function new(name:String, isPlayerA:Bool)
	{
		super();
		_name = name;

		var nameText:TextField = new TextField();
		nameText.setTextFormat(new TextFormat(AssetCache.font.fontName, 80));
		nameText.autoSize = TextFieldAutoSize.LEFT;
		nameText.textColor = isPlayerA ? AssetCache.darkA : AssetCache.darkB;
		nameText.text = name;

		var statusScore:Bitmap = new Bitmap(AssetCache.statusScore);
		var statusEnergy:Bitmap = new Bitmap(AssetCache.statusEnergy);
		var statusHealth:Bitmap = new Bitmap(AssetCache.statusHealth);
		var statusDamage:Bitmap = new Bitmap(AssetCache.statusDamaage);

		statusScore.y = 2;
		statusEnergy.y = statusScore.y + statusScore.height + 4;
		statusHealth.y = statusEnergy.y + statusEnergy.height + 4;
		statusDamage.y = statusHealth.y + statusHealth.height + 4;

		scoreText = statusText(isPlayerA);
		energyText = statusText(isPlayerA);
		healthText = statusText(isPlayerA);
		damageText = statusText(isPlayerA);

		scoreText.y = statusScore.y - 2;
		energyText.y = statusEnergy.y -2;
		healthText.y = statusHealth.y -2;
		damageText.y = statusDamage.y -2;

		statusScore.x = (isPlayerA) ? scoreText.width + 4 : 0;
		statusEnergy.x = (isPlayerA) ? energyText.width + 4 : 0;
		statusHealth.x = (isPlayerA) ? healthText.width + 4 : 0;
		statusDamage.x = (isPlayerA) ? damageText.width + 4 : 0;
		
		scoreText.x = (isPlayerA) ? 0 : statusScore.width + 4;
		energyText.x = (isPlayerA) ? 0 : statusEnergy.width + 4;
		healthText.x = (isPlayerA) ? 0 : statusHealth.width + 4;
		damageText.x = (isPlayerA) ? 0 : statusDamage.width + 4;

		var statusGroup:Group = new Group();
		statusGroup.addChild(statusScore);
		statusGroup.addChild(statusEnergy);
		statusGroup.addChild(statusHealth);
		statusGroup.addChild(statusDamage);
		statusGroup.add(scoreText);
		statusGroup.add(energyText);
		statusGroup.add(healthText);
		statusGroup.add(damageText);

		nameText.x = (isPlayerA) ? statusGroup.width + 6 : 0;
		statusGroup.x = (isPlayerA) ? 0 : nameText.width;

		addChild(statusGroup);
		addChild(nameText);

	}

	var format:TextFormat;
	var scoreText:TextField;
	var energyText:TextField;
	var healthText:TextField;
	var damageText:TextField;
	function statusText(expandLeft:Bool):TextField
	{
		if (format == null)
		{
			format = new TextFormat(AssetCache.font.fontName, 16);
			format.align = (expandLeft) ? TextFormatAlign.RIGHT : TextFormatAlign.LEFT;
		}

		var tf:TextField = new TextField();
		tf.setTextFormat(format);
		tf.text = "0000";
		tf.height = tf.textHeight;

		return tf;
	}

	public function update(player:Dynamic)
	{
		try
		{
			scoreText.text = player.score;
			energyText.text = player.energy;
			healthText.text = player.health;
			damageText.text = player.hitsTaken;
			var playerType:String = player.playerType;
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}
}