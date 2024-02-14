package tel.jeelpa.plugger.pluginloader

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tel.jeelpa.plugger.ManifestParser
import tel.jeelpa.plugger.PluginConfiguration
import tel.jeelpa.plugger.PluginLoader
import tel.jeelpa.plugger.PluginMetadata
import tel.jeelpa.plugger.PluginRepo


class ApkPluginManifestParser(
    private val pluginConfig: PluginConfiguration
): ManifestParser<ApplicationInfo> {
    override fun parseManifest(data: ApplicationInfo): PluginMetadata {
        return PluginMetadata(
            path = data.sourceDir,
            className = data.metaData.getString(pluginConfig.metadataSourceClassTag)
                ?: error("ClassName not found in Metadata"),
        )
    }
}

class ApkPluginLoader<TPlugin>(
    private val ctx: Context,
    private val configuration: PluginConfiguration
) : PluginRepo<TPlugin> {

    companion object {

        @Suppress("Deprecation")
        val PACKAGE_FLAGS = PackageManager.GET_CONFIGURATIONS or
                PackageManager.GET_META_DATA or
                PackageManager.GET_SIGNATURES or
                (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) PackageManager.GET_SIGNING_CERTIFICATES else 0)

    }

    // initialize class variables
    private val pluginLoader = PluginLoader(ctx)
    private val apkManifestParser = ApkPluginManifestParser(configuration)

    private fun getStaticPlugins(): List<TPlugin> {
        return ctx.packageManager
            .getInstalledPackages(PACKAGE_FLAGS)
            .filter {
                it.reqFeatures.orEmpty().any {
                    featureInfo ->  featureInfo.name == configuration.featureName
                }
            }
            .map { apkManifestParser.parseManifest(it.applicationInfo) }
            .map { pluginLoader<TPlugin>(it) }
            .toList()
    }

    // TODO: Listen for app installation broadcasts and update flow on change
    override fun getAllPlugins(): Flow<List<TPlugin>> =
        flowOf(getStaticPlugins())
}