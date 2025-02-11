import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter

class CsvWriter {
    @Throws(IOException::class)
    fun writeToCsv(path: String, data: List<String>) {
        try {
            PrintWriter(FileWriter(path, true)).use { pw ->
                val csvRow = data.joinToString(",")
                pw.println(csvRow)
                println("Data successfully added to $path")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
