package ninjabrainbot.io.preferences;

import java.util.HashMap;

import ninjabrainbot.event.Modifiable;

public class MultipleChoiceStringPreference extends Modifiable<String> {

	IPreferenceSource pref;

	String key;
	String value;
	String[] choices;

	HashMap<Integer, String> id2choice;
	HashMap<String, Integer> choice2id;

	public MultipleChoiceStringPreference(String key, String defaultValue, int[] ids, String[] choices, IPreferenceSource pref) {
		this.pref = pref;
		this.key = key;
		this.choices = choices;
		id2choice = new HashMap<Integer, String>();
		choice2id = new HashMap<String, Integer>();
		for (int i = 0; i < ids.length; i++) {
			id2choice.put(ids[i], choices[i]);
			choice2id.put(choices[i], ids[i]);
		}
		value = id2choice.get(pref.getInt(key, choice2id.get(defaultValue)));
	}

	public String get() {
		return value;
	}

	public void set(String value) {
		this.value = value;
		pref.putInt(key, choice2id.get(value));
		notifySubscribers(value);
	}

	public String[] getChoices() {
		return choices;
	}

}