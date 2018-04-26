package avdw.haxe.replayviewer;

import haxe.Json;
import openfl.display.Sprite;
import openfl.Lib;
import openfl.events.MouseEvent;
import sys.FileSystem;
import sys.io.File;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class Main extends Sprite
{

	public function new()
	{
		super();

		// Assets:
		// openfl.Assets.getBitmapData("img/assetname.jpg");
		try
		{
			var config = Json.parse(File.getContent('C:/Users/CP318674/Documents/alysm/replay-viewer/config.json'));
			matchBrowser(config.matchDirectory);
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}
	
	function matchBrowser(matchDirectory) {
		var matches = FileSystem.readDirectory(matchDirectory);
		
		var idx = 0;
		for (match in matches) {
			var rounds = FileSystem.readDirectory(matchDirectory + match);
			var btn:Button = new Button(match + " " + rounds.length, function(e:MouseEvent) {
				trace(matchDirectory + match);
			}, 0, idx * 100);
			addChild(btn);
			idx++;
		}
	}

}
