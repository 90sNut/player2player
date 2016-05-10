package org.ninetysnut.playertoplayer;

import javax.sound.sampled.AudioFormat;

public class Utils {

	/**
	 * @return the mono audio data format to be used for capturing/playback.
	 */
	public static AudioFormat getMonoAudioFormat() {
		return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 22050.0F, 8,
				1, 1, 22050.0F, false);
	}
}
