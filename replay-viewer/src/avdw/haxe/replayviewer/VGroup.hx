package avdw.haxe.replayviewer;
import openfl.display.Sprite;
import openfl.events.Event;
import openfl.events.MouseEvent;

/**
 * ...
 * @author Andrew van der Westhuizen
 */
class VGroup extends Sprite
{
	var sprites:Array<Sprite> = new Array();
	var invalid:Bool = true;

	public function new()
	{
		super();
		addEventListener(Event.ENTER_FRAME, function(e:Dynamic){
			if (invalid) {
				resize();
				invalid = false;
			}
		});
	}

	public function add(sprite:Sprite)
	{
		sprites.push(sprite);
		addChild(sprite);
		invalidate();
	}
	
	public function invalidate() 
	{
		this.invalid = true;
	}

	public function resize()
	{
		try
		{
			var maxHeight:Int = 0;
			for (sprite in sprites)
			{
				maxHeight = cast Math.max(sprite.height, maxHeight);
			}

			var idx:Int = 0;
			for (sprite in sprites)
			{
				sprite.y = idx * Math.ceil(maxHeight);
				idx++;
			}
		}
		catch (e:Dynamic)
		{
			trace(e);
		}
	}
	
	public function removeAll() 
	{
		for (sprite in sprites) {
			removeChild(sprite);
		}
		
		while (sprites.length > 0) {
			sprites.pop();
		}
	}

}