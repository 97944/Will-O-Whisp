package wow;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

//便利なメソッドまとめ＠南波
public class OtherLogic {

	// MultipartFile を File へ変換
	// 変換するとプロジェクトにファイルが保存されてしまうので直したい
	public static File multiPartFileToFlie(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	/**
	 * バイト配列を16進数の文字列に変換する。 asHex
	 *
	 * @param bytes
	 *            バイト配列
	 * @return 16進数の文字列
	 */
	public static String bytesToHexString(byte bytes[]) {
		// バイト配列の２倍の長さの文字列バッファを生成。
		StringBuffer strbuf = new StringBuffer(bytes.length * 2);

		// バイト配列の要素数分、処理を繰り返す。
		for (int index = 0; index < bytes.length; index++) {
			// バイト値を自然数に変換。
			int bt = bytes[index] & 0xff;

			// バイト値が0x10以下か判定。
			if (bt < 0x10) {
				// 0x10以下の場合、文字列バッファに0を追加。
				strbuf.append("0");
			}

			// バイト値を16進数の文字列に変換して、文字列バッファに追加。
			strbuf.append(Integer.toHexString(bt));
		}

		/// 16進数の文字列を返す。
		return strbuf.toString();
	}

	/**
	 * 16進数の文字列をバイト配列に変換する。 asByteArray
	 *
	 * @param hex
	 *            16進数の文字列
	 * @return バイト配列
	 */
	public static byte[] hexStringToBytes(String hex) {
		// 文字列長の1/2の長さのバイト配列を生成。
		byte[] bytes = new byte[hex.length() / 2];

		// バイト配列の要素数分、処理を繰り返す。
		for (int index = 0; index < bytes.length; index++) {
			// 16進数文字列をバイトに変換して配列に格納。
			bytes[index] = (byte) Integer.parseInt(hex.substring(index * 2, (index + 1) * 2), 16);
		}

		// バイト配列を返す。
		return bytes;
	}

	/**
	 * イメージ→バイト列に変換 getBytesFromImage
	 *
	 * @param img
	 *            イメージデータ
	 * @param format
	 *            フォーマット名
	 * @return バイト列
	 */
	public static byte[] bImageToBytes(BufferedImage img, String format) throws IOException {
		if (format == null) {
			format = "png";
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, format, baos);
		return baos.toByteArray();
	}

	/**
	 * バイト列→イメージを作成 getImageFromBytes
	 *
	 * @param bytes
	 * @return イメージデータ
	 */
	public static BufferedImage bytesToBImage(byte[] bytes) throws IOException {
		ByteArrayInputStream baos = new ByteArrayInputStream(bytes);
		BufferedImage img = ImageIO.read(baos);
		return img;
	}

	// MultipartFile を 16進数文字列に変換
	public static String multipartFileToHexString(MultipartFile inFile) {
		File file = null;
		BufferedImage image = null;
		byte[] byteString = null;
		String hexString = null;
		try {
			file = OtherLogic.multiPartFileToFlie(inFile);
			image = ImageIO.read(file);
			byteString = OtherLogic.bImageToBytes(image, "png");
			hexString = OtherLogic.bytesToHexString(byteString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hexString;
	}

	// 16進数文字列をバイト配列に変換し、ファイル名 newFileName で保存
	public static void saveImageFromHexString(String hexString, String newFileName) {
		byte[] byteString = null;
		String targetDir = "C:\\Users\\97944\\Documents\\workspace-sts-3.8.2.RELEASE\\wow\\src\\main\\resources\\static\\img\\usersPicture\\";
		try {
			// 16進数文字列を画像に変換
			byteString = OtherLogic.hexStringToBytes(hexString);

			// 画像書き込み
			BufferedOutputStream uploadFileStream;
			uploadFileStream = new BufferedOutputStream(new FileOutputStream(targetDir + newFileName));
			uploadFileStream.write(byteString);
			uploadFileStream.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// yyyyMMddHHmmss形式で現在時間を String で返す
	public static String nowDateTimeID() {
		String today = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		today = sdf.format(calendar.getTime());
		return today;
	}

	public static String nowDateTimeRegist() {
		String today = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		today = sdf.format(calendar.getTime());
		return today;
	}
}