package utilities;

import main.GameClass;

public class Constants {
	
	public static class EnemyConstants {

		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;

		public static final int CRABBY_WIDTH_DEFAULT = 72;
		public static final int CRABBY_HEIGHT_DEFAULT = 32;

		public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * GameClass.SCALE);
		public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * GameClass.SCALE);

		public static int getAnimationCount(int action) {

			switch (action) {
				case IDLE:
					return 7;
				case RUNNING:
					return 6;
				case ATTACK:
					return 3;
				case HIT:
					return 4;
				case DEAD:
					return 5;
			}

			return 0;

		}

	}
	
	// user interface constants
	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * GameClass.SCALE);
			public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * GameClass.SCALE);
		}
		
		public static class PauseButtons {
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * GameClass.SCALE);
		}
		
		public static class URMButtons{
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int)(URM_DEFAULT_SIZE * GameClass.SCALE);
		}
	}
	
	// Static inner class holds the user movements as static constants.
	public static class MovementConstants {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	// Static inner class that holds the different types of animations as static
	// constant variables. These variables signify the row in the animations array.
	public static class AnimationConstants	{
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int ATTACK = 4;
		public static final int HIT = 5;
		public static final int DEAD = 6;
		
		// Based on user action, returns the number of corresponding character animations
		// for each state. Used to signify the columns in the animation array.
		public static int getAnimationCount(int action) {
			
			switch(action) {
			case DEAD:
				return 8;
			case RUNNING:
				return 6;
			case IDLE:
				return 5;
			case HIT:
				return 4;
			case JUMP:
			case ATTACK:
				return 3;
			case FALLING:
			default:
				return 1;
				
			}
		}
	}
}
