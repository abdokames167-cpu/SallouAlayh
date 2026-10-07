package com.salloualayh.app

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val prefs by lazy { getSharedPreferences("sallou", MODE_PRIVATE) }
    private lateinit var counter: TextView

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counter = findViewById(R.id.counter)
        refreshCounter()

        findViewById<Button>(R.id.addButton).setOnClickListener {
            val n = prefs.getInt("salawat_today", 0) + 1
            prefs.edit().putInt("salawat_today", n).apply()
            refreshCounter()
        }

        findViewById<Button>(R.id.notificationsButton).setOnClickListener {
            requestNotificationPermission()
            if (!Settings.canDrawOverlays(this)) {
                startActivity(Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                ))
            } else {
                Toast.makeText(this, "تم تفعيل نافذة الذكر العابر.", Toast.LENGTH_SHORT).show()
            }
        }

        buildSections()
        requestNotificationPermission()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun refreshCounter() {
        counter.text = prefs.getInt("salawat_today", 0).toString()
    }

    private fun buildSections() {
        val container = findViewById<LinearLayout>(R.id.categories)
        val sections = listOf(
            "🌅 أذكار الصباح",
            "🌙 أذكار المساء",
            "☀️ أذكار الاستيقاظ",
            "😴 أذكار النوم",
            "🕌 أذكار بعد الصلاة",
            "🏠 أذكار دخول وخروج المنزل",
            "🕌 أذكار دخول وخروج المسجد",
            "🍽️ أذكار الطعام",
            "✈️ أذكار السفر",
            "📖 أدعية من القرآن والسنة",
            "📿 التسبيح والاستغفار",
            "❤️ الصلاة على النبي ﷺ"
        )
        sections.forEach { title ->
            val button = Button(this).apply {
                text = title
                textSize = 17f
                setOnClickListener {
                    Toast.makeText(this@MainActivity, "$title — القسم قيد التجهيز في النسخة الأولى", Toast.LENGTH_SHORT).show()
                }
            }
            container.addView(button)
        }
    }
}
