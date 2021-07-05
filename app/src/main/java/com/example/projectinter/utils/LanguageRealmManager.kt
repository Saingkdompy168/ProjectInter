package com.chipmong.dms.utils

import android.content.Context
import android.util.Log
import io.realm.Case
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults

object LanguageRealmManager {

    private var realm: Realm? = null

    init {
        realm = Realm.getInstance(
            RealmConfiguration.Builder()
                .name("Language.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build()
        )
    }

//    fun clear() {
//        realm?.beginTransaction()
//        realm?.delete(WordRealmObject::class.java)
//        realm?.commitTransaction()
//        realm?.close()
//    }
//
//    fun saveLanguage(wordRealmObjects: ArrayList<WordRealmObject>) {
//        realm?.beginTransaction()
//        realm?.delete(WordRealmObject::class.java)
//        realm?.insertOrUpdate(wordRealmObjects)
//        realm?.commitTransaction()
//        realm?.close()
//    }
//
//    /**
//     * SaveLanguage from Json array
//     */
//    fun saveLanguage(jsonArray: String) {
//        realm?.executeTransaction {
//            realm?.delete(WordRealmObject::class.java)
//            it.createOrUpdateAllFromJson(WordRealmObject::class.java, jsonArray)
//        }
//    }
//
//    fun getWords(): RealmResults<WordRealmObject>? {
//        return realm?.where(WordRealmObject::class.java)?.findAll()
//    }

//    fun getWord(word: String, context: Context): String {
//
//        var languageCode = if (DmsSharePreference.getInstance(context).getSelectedLanguage()) "km" else "en"
//        return try {
//            val wordRealm = realm?.where(WordRealmObject::class.java)
//                ?.equalTo("key", word)?.equalTo("languageCode", languageCode)
//                ?.findFirst()
//
//            if (wordRealm != null && !TextUtils.isEmpty(wordRealm.value))
//
//                realm?.where(WordRealmObject::class.java)?.equalTo("key", word)?.equalTo(
//                    "languageCode",
//                    languageCode
//                )?.findFirst()?.value!!
//            else {
//                languageCode = "de"
//                realm?.where(WordRealmObject::class.java)?.equalTo("key", word)?.equalTo(
//                    "languageCode",
//                    languageCode
//                )?.findFirst()?.value!!
//            }
//        } catch (e: Exception) {
//            Log.e(
//                "LanguageRealmManager",
//                " (Activity : " + javaClass.simpleName + ") Missing \"" + word + "\" in " + languageCode
//            )
//            word
//        }
//    }

//    fun getWord(word: String, context: Context): String {
//        val languageCode =
//            if (DmsSharePreference.getInstance(context).getSelectedLanguage()) "km" else "en"
//        return try {
//            val wordRealm = realm?.where(WordRealmObject::class.java)
//                ?.equalTo("code", word, Case.INSENSITIVE)
//                ?.findFirst()
//            wordRealm?.load()
//            if (wordRealm?.locales?.locale != null) {
//                val locales = wordRealm.locales?.locale
//                if (languageCode.equals("km", true)) {
//                    locales?.km?.name ?: word
//                } else {
//                    locales?.en?.name ?: word
//                }
//            } else {
//                word
//            }
//        } catch (e: Exception) {
//            Log.e(
//                "LanguageRealmManager",
//                " (Activity : " + javaClass.simpleName + ") Missing \"" + word + "\" in " + languageCode
//            )
//            word
//        }
//    }
}