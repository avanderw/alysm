package avdw.haxe.replayviewer;

import haxe.Json;
import openfl.Assets;
import openfl.display.BitmapData;
import openfl.display.Sprite;
import openfl.display.StageAlign;
import openfl.display.StageScaleMode;
import openfl.events.Event;
import openfl.filters.GlowFilter;
import openfl.text.Font;
import openfl.text.TextField;
import openfl.text.TextFieldAutoSize;
import openfl.text.TextFormat;
import sys.FileSystem;
import sys.io.File;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class Main extends Sprite
{
	var matchList:ButtonGroup;
	var roundList:ButtonGroup;
	var gameMap:Group;
	var config:Dynamic;
	var font:Font;
	var shield:BitmapData;
	var attackBuildingBmd:BitmapData;
	var energy:BitmapData;
	var fields:BitmapData;
	var warning:BitmapData;
	var missile:BitmapData;
	var gameDetails:GameDetails;
	var playerDetails:PlayerDetails;
	var roundText:TextField;
	var glowFilter:GlowFilter = new GlowFilter(0x333333, 1, 3, 3, 16);

	public function new()
	{
		super();
		// Assets:
		// openfl.Assets.getBitmapData("img/assetname.jpg");
		try
		{
			new AssetCache();
			font = Assets.getFont("font/OpenSans-Regular.ttf");

			shield = Assets.getBitmapData("img/64x-building-defense.png");
			attackBuildingBmd = Assets.getBitmapData("img/64x-building-attack.png");
			energy = Assets.getBitmapData("img/64x-building-energy.png");
			fields = AssetCache.buildingEmpty;
			warning = Assets.getBitmapData("img/warning.png");
			missile = Assets.getBitmapData("img/missile.png");

			#if debug
			config = Json.parse(File.getContent('../../../config.json'));
			#else
			if (!FileSystem.exists('./config.json')) {
				trace("config.json does not exist in replay-viewer.exe directory");
				Sys.exit(1);
			}
			config = Json.parse(File.getContent('./config.json'));
			#end

			addChild(matchList = new ButtonGroup());
			addChild(roundList = new ButtonGroup());
			addChild(gameMap = new Group());

			addEventListener(Event.ADDED_TO_STAGE, added);
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	function added(e:Event):Void
	{
		removeEventListener(Event.ADDED_TO_STAGE, added);

		stage.scaleMode = StageScaleMode.NO_SCALE;
		stage.align = StageAlign.TOP_LEFT;

		updateMatchList();
	}

	function updateMatchList()
	{
		var playerA;
		var bName:String;
		var aName:String;
		try
		{
			var matches = FileSystem.readDirectory(config.matchDirectory);

			for (match in matches)
			{
				var rounds = FileSystem.readDirectory(config.matchDirectory + "/" + match);
				var players = FileSystem.readDirectory(config.matchDirectory + "/" + match + "/Round " + pad(rounds.length - 1));
				if (players[0] != "endGameState.txt")
				{
					if (players[0] != "Player 1")
					{
						aName = players[0].substring(4);
						bName = players[1].substring(4);
					}
					else {
						aName = players[0];
						bName = players[1];
					}
					playerA = players[0];
				}
				else
				{
					if (players[1] != "Player 1")
					{
						aName = players[1].substring(4);
						bName = players[2].substring(4);
					}
					else {
						aName = players[1];
						bName = players[2];
					}
					playerA = players[1];
				}
				//trace(playerA);
				var map = Json.parse(File.getContent(config.matchDirectory + "/" + match + "/Round " + pad(rounds.length-1) + "/"+playerA+"/JsonMap.json"));
				//trace(map.players);
				var btn = new Button(aName+":"+map.players[0].score+"\n"+bName+":"+map.players[1].score+"\nRounds " + rounds.length, function(e:Dynamic)
				{
					updateRoundList(config.matchDirectory + "/" + match);
				}, map.players[0].score > map.players[1].score ? AssetCache.darkWin : AssetCache.darkLose,
				map.players[0].score > map.players[1].score ? AssetCache.lightWin : AssetCache.lightLose);
				matchList.add(btn);
			}
		}
		catch (e:Dynamic)
		{
			trace("ERR:" + e);
			Sys.exit(1);
		}
	}

	function pad(length:Int):String
	{
		if (length < 10)
		{
			return "00" +length;
		}
		else if (length < 100)
		{
			return return "0" + length;
		}
		else{
			return cast length;
		}
	}

	function updateRoundList(directory:String)
	{
		try
		{
			var rounds = FileSystem.readDirectory(directory);
			roundList.removeAll();
			for (round in rounds)
			{
				var players = FileSystem.readDirectory(directory + "/" + round);
				var playerA = null;
				if (players[0] != "endGameState.txt")
				{
					playerA = players[0];
				}
				else
				{
					playerA = players[1];
				}
				var roundData = Json.parse(File.getContent(directory + "/" + round + "/"+playerA+"/JsonMap.json"));
				var playerCommand = File.getContent(directory + "/" + round + "/"+playerA+"/playerCommand.txt");
				var dark = 0x0;
				var light = 0xdddddd;
				var commandText = "";
				switch (playerCommand.substring(playerCommand.lastIndexOf(",") +1))
				{
					case "0": commandText = playerCommand.substring(0, playerCommand.lastIndexOf(",")) + ",Defend"; dark = AssetCache.darkDefend; light = AssetCache.lightDefend;
					case "1": commandText = playerCommand.substring(0, playerCommand.lastIndexOf(",")) + ",Attack"; dark = AssetCache.darkAttack; light = AssetCache.lightAttack;
					case "2": commandText = playerCommand.substring(0, playerCommand.lastIndexOf(",")) + ",Energy"; dark = AssetCache.darkEnergy; light = AssetCache.lightEnergy;
					default: commandText = playerCommand;
				}

				var btn = new Button(round +
									 "\nEnergy:" + roundData.players[0].energy
									 //"\nH: " + roundData.players[0].health +
									 //"\n" + commandText
									 , function(e:Dynamic)
				{
					gameMap.removeAll();
					loadRound(directory + "/" + round);
				}, dark, light);
				roundList.add(btn);
			}
		}
		catch (e:Dynamic)
		{
			trace("ERR:"+e);
		}
	}

	function loadRound(directory:String)
	{
		try
		{
			var buildingSize:Int = 64;
			var spacingSize:Int = 4;
			var players = FileSystem.readDirectory(directory);
			var map = Json.parse(File.getContent(directory + "/"+players[0]+"/JsonMap.json"));

			if (gameDetails == null)
			{
				roundList.x = matchList.width;
				gameDetails = new GameDetails(map.gameDetails);
				gameDetails.x = stage.stageWidth - gameDetails.width;
				gameDetails.y = stage.stageHeight - gameDetails.height;
				addChild(gameDetails);
			}

			if (roundText == null)
			{
				roundText = new TextField();
				roundText.setTextFormat(new TextFormat(AssetCache.font.fontName, 64));
				addChild(roundText);
			}

			roundText.text = ":Round " + map.gameDetails.round;
			roundText.autoSize = TextFieldAutoSize.LEFT;
			roundText.y = (gameDetails.height - roundText.height) / 2;

			//trace(map);
			//trace(map.gameMap);
			var energyGrowthA:Int = 5;
			var energyGrowthB:Int = 5;
			for (row in cast (map.gameMap, Array<Dynamic>))
			{
				for (cell in cast (row, Array<Dynamic>))
				{
					//trace(cell, cell.cellOwner);

					var buildings:Array<Dynamic> = cell.buildings;
					var buildingBmp:Building = (buildings.length > 0) ? new Building(buildings[0]) : new Building({});
					if (buildings.length > 1)
					{
						throw "more than one building on a cell, handle this";
					}
					buildingBmp.graphics.lineStyle(2, cell.cellOwner == "A" ? AssetCache.darkA : AssetCache.darkB);
					buildingBmp.graphics.beginFill(cell.cellOwner == "A" ? AssetCache.lightB : AssetCache.lightB);
					buildingBmp.graphics.drawRect(0, 0, buildingBmp.width+4, buildingBmp.height+4);
					buildingBmp.graphics.endFill();

					var missiles:Array<Dynamic> = cell.missiles;
					var missileGroup:Group = new Group();
					if (missiles.length > 0)
					{
						/*if (missiles.length > 2)
						{
							throw "more than two missiles on a cell, handle this";
						}*/

						var missilesA:Array<Missile> = new Array();
						var missilesB:Array<Missile> = new Array();
						for (missile in missiles)
						{
							if (missile.playerType == "A")
							{
								missilesA.push(new Missile(missile));
							}
							else if (missile.playerType == "B")
							{
								missilesB.push(new Missile(missile));
							}
						}

						for (m in 0...missilesA.length)
						{
							var missile:Missile = missilesA[m];
							missile.x = m * 5;
							missile.y = m * 2 - 6;
							missile.filters = [glowFilter];
							missileGroup.add(missile);
						}

						for (m in 0...missilesB.length)
						{
							var missile:Missile = missilesB[m];
							missile.x = m * 5;
							missile.y = buildingBmp.height - missile.height + m * 2 + 4;
							missile.filters = [glowFilter];
							missileGroup.add(missile);
						}

						/*var m1:Missile = new Missile(missiles[0]);
						m1.y = -6;
						m1.x = (buildingBmp.width - m1.width) / 2;
						missileGroup.add(m1);
						if (missiles.length > 1)
						{
							var m2:Missile = new Missile(missiles[1]);
							m2.y = buildingBmp.height - m2.height + 6;
							m2.x = (buildingBmp.width - m1.width) / 2;
							missileGroup.add(m2);
						}*/

					}
					missileGroup.x = (buildingBmp.width - missileGroup.width) / 2;

					var sprite:Sprite = new Sprite();
					sprite.addChild(buildingBmp);
					sprite.addChild(missileGroup);
					sprite.x = cell.x * (buildingSize + spacingSize);
					sprite.y = cell.y * (buildingSize + spacingSize);
					gameMap.add(sprite);

					if (cell.cellOwner == "A")
					{
						switch (buildingBmp.type)
						{
							case "ENERGY": energyGrowthA += buildingBmp.energy;
						}
					}
					else
					{
						switch (buildingBmp.type)
						{
							case "ENERGY": energyGrowthB += buildingBmp.energy;
						}
					}

				}
			}
			if (playerDetails == null)
			{
				addChild(playerDetails = new PlayerDetails());
			}

			playerDetails.update(map.players, energyGrowthA, energyGrowthB);

			if (positionOnce)
			{
				playerDetails.x = roundList.x + roundList.width + 5;
				playerDetails.y = (gameDetails.height - playerDetails.height) / 2;
				gameMap.x = playerDetails.x;
				gameMap.y = gameDetails.height + 5;
				//playerDetails.x = gameMap.x + (gameMap.width - playerDetails.width) / 2;
				//playerDetails.y = gameMap.y + gameMap.height;
				roundText.x = (playerDetails.x + playerDetails.width);
				positionOnce = false;
			}
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}

	var positionOnce:Bool = true;

}
