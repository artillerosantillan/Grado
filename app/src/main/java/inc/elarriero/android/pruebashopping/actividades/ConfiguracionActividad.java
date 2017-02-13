package inc.elarriero.android.pruebashopping.actividades;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import inc.elarriero.android.pruebashopping.R;
import inc.elarriero.android.pruebashopping.infraestructura.Utils;

public class ConfiguracionActividad extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new OrdenarPreferenciaFragment())
                .commit();

// ****************** Orgdenar por tipos  la Nombre Lista Compras*********************

    }

    public static class OrdenarPreferenciaFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferencia_general);
            bindPreferenciaResumenPorValor(findPreference(getString(R.string.pref_sort_list_name)));


        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            setPreferenciasResumen(preference, newValue);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Utils.LISTA_ORDEN_PREFERENCE, newValue.toString()).apply();
            return true;
        }

        private void bindPreferenciaResumenPorValor(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            setPreferenciasResumen(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));

        }

        private void setPreferenciasResumen(Preference preference, Object value) {
            String stringValue = value.toString();


            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int preferenceIndex = listPreference.findIndexOfValue(stringValue);

                if (preferenceIndex >= 0) {
                    preference.setSummary(listPreference.getEntries()[preferenceIndex]);
                }
            }
        }
    }


}
