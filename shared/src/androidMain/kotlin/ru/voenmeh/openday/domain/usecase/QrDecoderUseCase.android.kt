package ru.voenmeh.openday.domain.usecase

import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import ru.voenmeh.openday.domain.constants.Constants
import ru.voenmeh.openday.domain.model.Quest
import ru.voenmeh.openday.domain.repository.QuestRepository
import ru.voenmeh.openday.domain.utils.Log


actual class QrDecoderUseCase(private val activity: AppCompatActivity, actual val questRepository: QuestRepository) {
    private var onSuccess: (String) -> Unit = {}

    val barcodeLauncher: ActivityResultLauncher<ScanOptions> = activity.registerForActivityResult(
        ScanContract()
    ) { result ->
        if (result.contents == null) {
            Toast.makeText(activity, Constants.Strings.scanQrError, Toast.LENGTH_LONG).show()
        } else {
            onSuccess(result.contents)
        }
    };

    actual fun openCamera(onSuccess: (String) -> Unit) {
        this.onSuccess = onSuccess

        val scanOption = ScanOptions()

        scanOption.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        scanOption.setPrompt(Constants.Strings.scanQr)
        scanOption.setCameraId(0)
        scanOption.setBeepEnabled(false)
        scanOption.setBarcodeImageEnabled(true)
        scanOption.setOrientationLocked(false)

        barcodeLauncher.launch(scanOption)
    }

    actual fun decodeQR(questJson: String): Quest {
        return questRepository.decodeQuest(questJson)
    }

}