package br.com.ninetysnut.playertoplayer;

import javax.sound.sampled.AudioFormat;

public class Utils {

	/**
	 * @return the mono audio data format to be used for capturing/playback.
	 */
	public static AudioFormat getMonoAudioFormat() {
		return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16,
				1, 2, 44100.0F, false);
	}
}
