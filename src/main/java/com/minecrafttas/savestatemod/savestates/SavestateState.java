package com.minecrafttas.savestatemod.savestates;

/**
 * The state in which the savestate is in
 * @author Scribble
 *
 */
public enum SavestateState {
	/**
	 * A savestate saving operation is currently being carried out
	 */
	SAVING,
	/**
	 * A savestate loading operation is currently being carried out
	 */
	LOADING,
	/**
	 * Nothing is being carried out, the savestate handler is ready to save or to load
	 */
	NONE
}
