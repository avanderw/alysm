package avdw.haxe.replayviewer;
import openfl.display.Bitmap;
import openfl.display.BitmapData;
import openfl.display.Sprite;
import openfl.text.TextField;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class Status extends Sprite
{

	public function new(bmd:BitmapData, value:Int) 
	{
		super();
		
		var valueTf:TextField = new TextField();
		valueTf.text = cast value;
		valueTf.x = bmd.width;
		
		addChild(new Bitmap(bmd));
		addChild(valueTf);
		
	}
	
}