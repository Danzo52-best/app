package com.tin.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tv_title, tv_second, tv_content;
    ProgressBar prb_time;
    RadioButton rdoA, rdoB, rdoC, rdoD;
    Button btn_answer, btn_exit;
    public static ArrayList<Question> questions;
    public static ArrayList<Question> plays;
    public static Random rand;
    public static int current;  //quesetion hien tai
    public static String choose; //cau tra loi
    public static CountDownTimer countDownTimer;
    Database database;

    public static void Init(){
        questions=new ArrayList<>();
        questions.add(new Question(1, "Nước ta nằm ở vị trí nào?", "Rìa phía Đông của bán đảo Đông Dương.", "Rìa phía Tây của bán đảo Đông Dương.", "Trung tâm châu Á", "Phía đông Đông Nam Á", "A" ));
        questions.add(new Question(2, "Nằm ở rìa phía Đông của bán đảo Đông Dương là nước nào?", "Lào", "Campuchia", "Việt Nam", "Mi-an-ma", "C" ));
        questions.add(new Question(3, "Điểm cực Bắc của nước ta là xã Lũng Cú thuộc tỉnh:", "Cao Bằng", "Hà Giang", "Yên Bái", "Lạng Sơn", "B" ));
        questions.add(new Question(4, "Vị trí địa lí của nước ta là:", "nằm ở phía Đông bán đảo Đông Dương, gần trung tâm khu vực Đông Nam Á", "nằm ở phía Tây bán đảo Đông Dương, gần trung tâm khu vực Đông Nam Á", "nằm ở phía Đông bán đảo Đông Dương, gần trung tâm khu vực châu Á", "nằm ở phía Tây bán đảo Đông Dương, gần trung tâm khu vực châu Á", "A" ));
        questions.add(new Question(5, "Điểm cực Đông của nước ta là xã Vạn Thạnh thuộc tỉnh:", "Ninh Thuận", "Khánh Hòa", "Đà Nẵng", "Phú Yên", "B" ));
        questions.add(new Question(6, "Ở tỉnh Khánh Hòa có một đặc điểm tự nhiên rất đặc biệt là:", "Là tỉnh duy nhất có nhiều đảo", "Là tỉnh có điểm cực Đông nước ta", "Là tỉnh có nhiều hải sản nhất", "Là tỉnh có nhiều than nhất", "B" ));
        questions.add(new Question(7, "Đâu không phải là đặc điểm của vị trí địa lí nước ta:", " vừa gắn liền với lục địa Á – Âu, vừa tiếp giáp với Thái Bình Dương.", "nằm trên các tuyến đường giao thông hàng hải, đường bộ, đường hàng không quốc", "trong khu vực có nền kinh tế năng động của thế giới.", "nằm ở trung tâm của châu Á.", "D" ));
        questions.add(new Question(8, "Nước ta nằm ở vị trí:", "rìa phía Đông của bán đảo Đông Dương", "trên bán đảo Ấn Độ.", "phía đông Đông Nam Á", "trung tâm châu Á - Thái Bình Dương.", "A" ));
        questions.add(new Question(9, "Vùng đất là:", "phần đất liền giáp biển", "toàn bộ phần đất liền và các hải đảo", "phần được giới hạn bởi các đường biên giới và đường bờ biển", "các hải đảo và vùng đồng bằng ven biển", "B" ));
        questions.add(new Question(10, "Nội thủy là:", "vùng nước tiếp giáp với đất liền, ở phía trong đường cơ sở.", "vùng nước tiếp giáp với lãnh hải", "vùng nước tiếp giáp với đặc quyền kinh tế", "vùng nước tiếp giáp với thềm lục địa", "A" ));
        questions.add(new Question(11, "Tài nguyên khoáng sản có giá trị lớn nhất ở vùng biển nước ta là", "sắt.", "dầu khí.", "ôxit titan.", "muối.", "B" ));
        questions.add(new Question(12, "Trong các chất sau đây: I. Thủy tinh; II: Kim Cương; III. Dung dịch bazơ; IV. Nước mưa. Những chất điện môi là:", "I và II", "III và IV", "I và IV", "II và III", "A" ));
        questions.add(new Question(13, "Trong các chất nhiễm điện : I. Do cọ sát; II. Do tiếp xúc; II. Do hưởng ứng. NHững cách nhiễm điện có thể chuyển dời electron từ vật này sang vật khác là:", "I và II", "III và II", "I và III", "Chỉ có III", "A" ));
        questions.add(new Question(14, "“Đấu tranh cho một thế giới hoà bình” của Mác-két được viết theo phương thức nào là chính?", "Nghị luận", "Biểu cảm", "Thuyết minh", "Tự sự", "C" ));
        questions.add(new Question(15, "Bài viết của Gác-xi-a Mác-két có mấy luận điểm chính?", "Ba", "Hai", "Một", "Bốn", "D" ));
        questions.add(new Question(16, "Văn bản “Tuyên bố thế giới về sự sống còn, quyền được bảo vệ và phát triển của trẻ em” được bố cục thành mấy phần chính?", "BA", "HAI", "BỐN", "NĂM", "C" ));
        questions.add(new Question(17, "Trong những câu hỏi sau câu hỏi nào không liên quan đến đặc điểm của tình huống giao tiếp?", "Nói ở đâu?", "Có nên nói quá không?", "Nói khi nào?", "Nói với ai?", "C" ));
        questions.add(new Question(18, "“Chuyện người con gái Nam Xương” được viết vào thế kỉ nào?", "Thế kỉ XVI", "Thế kỉ XIV", "Thế kỉ XV", "Thế kỉ XVII", "A" ));
        questions.add(new Question(19, "Bố cục của “Chuyện người con giá Nam Xương” được chia làm mấy phần?", "Ba", "Hai", "Bốn", "Năm", "A" ));
        questions.add(new Question(20, "“Chuyện cũ trong phủ chúa Trịnh” được viết theo thể loại nào”?", "Tuỳ bút", "Tiểu thuyết chương hồi", "Truyền kì", "Truyện ngắn", "C" ));
        questions.add(new Question(21, "Tác nhân sinh học có thể gây đột biến gen là", "vi khuẩn", "động vật nguyên sinh", "5BU", "virut hecpet", "D" ));
        questions.add(new Question(22, "Gen ban đầu có cặp nuclêôtit chứa A hiếm (A*) là T-A* , sau đột biến cặp này sẽ biến đổi thành cặp:", "T-A", "A-T", "G-X", "X-G", "D" ));
        questions.add(new Question(23, "Xét đột biến gen do 5BU, thì từ dạng tiền đột biến đến khi xuất hiện gen đột biến phải qua", "1 lần nhân đôi.", "2 lần nhân đôi.", "3 lần nhân đôi.", "4 lần nhân đôi.", "B" ));
        questions.add(new Question(24, "Biến đổi trên một cặp nuclêôtit của gen phát sinh trong nhân đôi ADN được gọi là", "đột biến", "đột biến gen.", "thể đột biến", "đột biến điểm", "D" ));
        questions.add(new Question(25, "Just think, __2 years' time, we'll be 20 both.", "under", "in", "after", "over", "B" ));
        questions.add(new Question(26, "It's said that he has ______ friends of his age.", "few", "plenty", "a little", "little", "A" ));
        questions.add(new Question(27, "The students ______ to be at school at 3 p.m.", "told", "have told", "were told", "tell", "C" ));
        questions.add(new Question(28, "He bought all the books ______ are needed for the English course.", "that", "who", "what", "those", "A" ));
        questions.add(new Question(29, "The existence of many stars in the sky ______ us to suspect that there may be life on another planet.", "have led", "leads", "lead", "leading", "B" ));
        questions.add(new Question(30, "______, we couldn't go out because it rained.", "Unfortunately", "Fortunate", "Fortunately", "Unfortunate", "A" ));
        questions.add(new Question(31, "The shirt in the window was ______ expensive for me to buy.", "too", "such", "enough", "so", "A" ));
        questions.add(new Question(32, "He couldn't get back _____ his car. He had locked himself out.", "on", "into", "in", "to", "B" ));
        questions.add(new Question(33, "Every member in Nataly's class admires her ______.", "honesty", "dishonest", "dishonesty", "honest", "A" ));
        questions.add(new Question(34, "John never comes to class on time and ______.", "neither does Peter", "so does Peter", "so doesn't Peter", "neither doesn't Peter", "A" ));
        questions.add(new Question(35, "If I were in charge, I ______ things differently.", "will do", "would do", "would have done", "had done", "B" ));
        questions.add(new Question(36, "The population of the earth is increasing at a tremendous rate and _______ out of control.", "are soon going to be", "soon will be", "they have become", "why it will be", "B" ));
        questions.add(new Question(37, "Janet has left home and is ______ of her parents.", "dependent", "dependently", "independent", "depend", "C" ));
        questions.add(new Question(38, "When I last ______ Jane, she ______ to find a job.", "saw/ was trying", "saw/ tried", "have seen/ tried", "see/ is trying", "A" ));
        questions.add(new Question(39, "______ he did not attend the English class, he knew the lesson quite well.", "In spite of", "However", "Although", "Despite", "C" ));
        questions.add(new Question(40, "He is going to get married ______ the end of this month.", "on", "in", "to", "at", "D" ));
        questions.add(new Question(41, "The air is not as pure as it ______.", "used to be", "is used to be", "is used to being", "was used to be", "A" ));
        questions.add(new Question(42, "After a meal in a restaurant, you ask the waiter for the _______.", "receipt", "cheque", "prescription", "bill", "D" ));
        questions.add(new Question(43, "He's a very ______ person because he can make other workers follow his advice.", "creative", "influential", "deciding", "effective", "B" ));
        questions.add(new Question(44, "Increasing ______ of fruit in the diet may help to reduce the risk of heart disease.", "the amount", "an amount", "the number", "a number", "A" ));
        questions.add(new Question(45, "\"Why wasn't your boyfriend at the party last night?\" - \"He ______ the lecture at Shaw Hall. I know he very much wanted to hear the speaker.\"", "should have attended", "can have attended", "was to attend", "may have attended", "D" ));
        questions.add(new Question(46, "The doctor decided to give her a thorough examination ______ he could identify the causes of her illness.", "after", "so as", "unless", "so that", "D" ));
        questions.add(new Question(47, "My computer is not ______ of running this software.", "able", "compatible", "capable", "suitable", "C" ));
        questions.add(new Question(48, "The room needs ______ for the wedding.", "decorating", "to decorate", "decorate", "be decorated", "A" ));
        questions.add(new Question(49, "That hotel is so expensive. They ______ you sixty pounds for bed and breakfast.", "charge", "fine", "take", "cost", "A" ));
        questions.add(new Question(50, "I am considering ______ my job. Can you recommend a good company?", "to move", "moving", "to change", "changing", "D" ));
        questions.add(new Question(51, "I'm sure you'll have no ______ the exam.", "difficulty passing", "difficulties to pass", "difficulty to pass", "difficulties of passing", "A" ));
        questions.add(new Question(52, "I'm afraid I'm not really ______ to comment on this matter.", "qualifying", "qualified", "quality", "qualitative", "B" ));
        questions.add(new Question(53, "Today, household chores have been made much easier by electrical", "utilities", "appliances", "appliances", "instruments", "C" ));
        questions.add(new Question(54, "The curtains have ______ because of the strong sunlight.", "faded", "fainted", "lightened", "weakened", "A" ));
        questions.add(new Question(55, "The referee ______ the coin to decide which team would kick the ball first.", "caught", "threw", "cast", "tossed", "D" ));
        questions.add(new Question(56, "I accidentally ______ Mike when I was crossing a street downtown yesterday.", "caught sight of", "kept an eye on", "paid attention to", "lost touch with", "A" ));
        questions.add(new Question(57, "How long does the play ______?", "last", "extend", "prolong", "stretch", "A" ));
        questions.add(new Question(58, "The price of fruit has increased recently, ______ the price of vegetables has gone down.", "whereas", "whether", "when", "otherwise", "A" ));
        questions.add(new Question(59, "", "have never known", "have never been knowing", "never know", "had never know", "A" ));
        questions.add(new Question(60, "When the old school friends met, a lot of happy memories ______ back.", "had brought", "were brough", "brought", "had been brought", "B" ));
        questions.add(new Question(61, "I ______ this letter around for days without looking at it.", "am carrying", "will be carrying", "carry ", "have been carrying", "D" ));
        questions.add(new Question(62, "If you are not Japanese, so what _______ are you?", "nationalized", "nation", "nationality", "national", "C" ));
        questions.add(new Question(63, "It was not until she had arrived home ______ remembered her appointment with the doctor.", "that she", "and she", "she", "when she had", "A" ));
        questions.add(new Question(64, "The manager had his secretary ______ the report for him.", "to have typed", "typed", "type", "to type", "C" ));
        questions.add(new Question(65, "Be ______ with what you have got, Mary.", "suspicious", "humorous", "interested", "satisfied", "D" ));
        questions.add(new Question(66, "He looks thin, but ______ he is very healthy.", "practically", "also", "actually", "consequently", "C" ));
        questions.add(new Question(67, "They would ______ go by air than travel by train", "always", "better", "prefer", "rather", "D" ));
        questions.add(new Question(68, "Don't worry. He'll do the job as _____ as possible.", "economizing", "economic", "uneconomically", "economically", "D" ));
        questions.add(new Question(69, "______ entering the hall, he found everyone waiting for him.", "With", "On", "At ", "During", "B" ));
        questions.add(new Question(70, "The window was so high up that ______ I could see was the sky.", "just", "all", "only", "thus", "B" ));
        questions.add(new Question(71, "He arrived late, ______ was annoying.", "it", "that", "what", "which", "D" ));
        questions.add(new Question(72, "I would really ______ your help with this assignment.", "respect", "take", "appreciate", "thank", "C" ));
        questions.add(new Question(73, "Can you keep calm for a moment? You ______ noise in class!", "are always made", "always make", "have always made", "are always making", "D" ));
        questions.add(new Question(74, "Take the number 5 bus and get ______ at Times Square.", "off", "up", "outside", "down", "A" ));
        questions.add(new Question(76, "I didn’t think his the comments were very appropriate at the time", "correct", "right", "exact", "suitable", "D" ));
        questions.add(new Question(77, "Khi đưa một quả cầu kim loại không nhiễm điện lại gần một quả cầu khác nhiễm điện thì", "Hai quả cầu đẩy nhau.", "Hai quả cầu hút nhau.", "Không hút mà cũng không đẩy nhau.", "Hai quả cầu trao đổi điện tích cho nhau.", "B" ));
        questions.add(new Question(78, "Vấn đề được nói tới trong văn bản “Phong cách Hồ Chí Minh” là gì?", "Phong cách làm việc và nếp sống của Chủ tịch Hồ Chí Minh.", "Tinh thần chiến đấu dũng cảm của Chủ tịch Hồ Chí Minh.", "Tình cảm của người dân Việt Nam đối với chủ tịch Hồ Chí Minh.", "Trí tuệ tuyệt vời của HCM", "A" ));
        questions.add(new Question(79, "Từ nào sau đây trái nghĩa với từ “truân chuyên”?", "Nhàn nhã", "gian nan", "nhọc nhằn", "vất vả", "C" ));
        questions.add(new Question(80, "Theo tác giả, để có được vốn tri thức sâu rộng về văn hoá, Chủ tịch Hồ Chí Minh đã làm gì?", "Cả  B, C, D đều đúng.", "Nắm vững phương tiện giao tiếp là ngôn ngữ", "học tập, tiếp thu có chọn lọc, phê phán.", "Đi nhiều nơi, làm nhiều nghề.", "A" ));
        questions.add(new Question(81, "Ý nào nói đúng nhất những phương tiện thể hiện lối sống giản dị của Chủ tịch Hồ Chí Minh?", "Cả  B, C, D đều đúng.", "Nơi ở và làm việc.", "Cách ăn uống và nơi ở", "Trang phục và ăn uống", "A" ));
        questions.add(new Question(82, "Để làm nổi bật lối sống rất giản dị của Chủ tịch Hồ Chí Minh, tác giả đã sử dụng phương thức lập luận nào?", "Chứng minh", "Giải thích", "Bình luận", "Phân tích", "B" ));
        questions.add(new Question(83, "Trong bài viết, tác giả so sánh lối sống của Bác Hồ với lối sống của những ai?", "Các danh nho Việt Nam thời xưa.", "Những vị lãnh tụ của các dân tộc trên thế giới.", "Các danh nho Trung Quốc thời xưa.", "Các vị lãnh đạo của nhà nước ta đương thời.", "B" ));
        questions.add(new Question(84, "Trong bài viết, để làm nổi bật phong cách Hồ Chí Minh, tác giả không sử dụng biện pháp nghệ thuật nào?", "Kết hợp giữa kể, bình luận, chứng minh.", "Sử dụng phép đối lập", "Sử dụng phép nói quá", "So sánh và sử dụng nhiều từ Hán Việt.", "D" ));
        questions.add(new Question(85, "Trong các từ sau, từ nào không phải là từ Hán Việt?", "vua", "lãnh tụ", "hiền triết", "danh nho", "D" ));
        questions.add(new Question(86, "“Đấu tranh cho một thế giới hoà bình” của Mác-két được viết theo phương thức nào là chính?", "Nghị luận", "Biểu cảm", "Thuyết minh", "Tự sự", "C" ));
        questions.add(new Question(87, "Bài viết của Gác-xi-a Mác-két có mấy luận điểm chính?", "Ba", "Hai", "Một", "Bốn", "D" ));
        questions.add(new Question(88, "Ý nào nói đúng nhất cách lập luận của Mac-két để người đọc hiểu rõ nguy cơ khủng khiếp của chiến tranh hạt nhân?", "Cả  B, C, D  đều đúng", "Đưa ra số liệu đầu đạn hạt nhân.", "Đưa ra những tính toán lí thuyết.", "Xác định thời gian cụ thể.", "A" ));
        questions.add(new Question(89, "Đặc sắc nhất về nghệ thuật lập luận của tác giả trong đoạn văn nói về các lĩnh vực y tế, thực phẩm, giáo dục ...là gì?", "Kết hợp giải thích và chứng minh.", "Lập luận giải thích", "Lập luận chứng minh", "Không phải các thao tác trên.", "C" ));
        questions.add(new Question(90, "Trong giao tiếp, nói lạc đề là vi phạm phương châm hội thoại nào?", "Phương châm quan hệ", "Phương châm về chất", "Phương châm về lượng", "Phương châm cách thức.", "A" ));
        questions.add(new Question(91, "Nói giảm, nói tránh là phép tu từ liên quan đến phương châm hội thoại nào?", "Phương châmcách thức", "Phương châm quan hệ", "Phương châm về chất", "Phương châm lịch sự.", "A" ));
        questions.add(new Question(92, "Bản tuyên bố này liên quan chủ yếu đến vấn đề nào trong đời sống xã hội của con người?", "Bảo vệ và chăm sóc trẻ em", "Bảo vệ và chăm sóc phụ nữ", "Bảo vệ môi trường sống", "Phát triển kinh tế xã hội", "B" ));
        questions.add(new Question(93, "Văn bản “Tuyên bố thế giới về sự sống còn, quyền được bảo vệ và phát triển của trẻ em” được bố cục thành mấy phần chính?", "BA", "HAI", "BỐN", "NĂM", "C" ));
        questions.add(new Question(94, "Nên đánh giá như thế nào về các nhiệm vụ đặt ra trong bản tuyên bố này?", "Cụ thể và toàn diện", "Chưa đầy đủ", "Không có tính khả thi", "Không phù hợp với thực tế.", "D" ));
        questions.add(new Question(95, "Những vấn đề nêu ra trong bản tuyên bố trực tiếp liên quan đến bối cảnh thế giới vào thời điểm nào?", "Những năm cuối thế kỉ XX", "Những năm đầu thế kỉ XX", "Những năm giữa thế kỉ XX.", "Những năm cuối thế kỉ XIX", "A" ));
        questions.add(new Question(96, "Để không vi phạm các phương châm hội thoại, cần phải làm gì?", "Nắm được các đặc điểm của tình huống giao tiếp.", "Hiểu rõ nội dung mình định nói", "Biết im lặng khi cần thiết", "Phối hợp nhiều cách nói khác nhau.", "D" ));
        questions.add(new Question(97, "Trong những câu hỏi sau câu hỏi nào không liên quan đến đặc điểm của tình huống giao tiếp?", "Nói ở đâu?", "Có nên nói quá không?", "Nói khi nào?", "Nói với ai?", "C" ));
        questions.add(new Question(98, "“Chuyện người con gái Nam Xương” được viết vào thế kỉ nào?", "Thế kỉ XVI", "Thế kỉ XIV", "Thế kỉ XV", " Thế kỉ XVII", "A" ));
        questions.add(new Question(99, "Bố cục của “Chuyện người con giá Nam Xương” được chia làm mấy phần?", "Ba", "Hai", "Bốn", "Năm", "A" ));
        questions.add(new Question(100, "Có mấy cách dẫn lời nói hay ý nghĩ của một người, một  nhân vật?", "Hai", "Một", "Ba", "Bốn", "A" ));
        questions.add(new Question(101, "“Chuyện cũ trong phủ chúa Trịnh” được viết theo thể loại nào”?", "Tuỳ bút", "Tiểu thuyết chương hồi", "Truyền kì", "Truyện ngắn", "C" ));
        questions.add(new Question(102, "Khi nói về cảnh vua Quang Trung cầm quân ra trận và trực tiếp chiến đấu tác giả chủ yếu dùng những kiểu câu nào?", "Câu kể (trần thuật)", "Câu cầu khiến", "Câu nghi vấn", "Câu cảm thán.", "B" ));
        questions.add(new Question(103, "Một chiếc xe buýt đang chạy từ trạm thu phí Thuỷ Phù lên Huế, nếu ta nói chiếc xe buýt đang đứng yên thì vật làm mốc là:", "Người soát vé đang đi lại trên xe", "Tài xế", "Trạm thu phí Thủy Phù", "Khu công nghiệm Phú Bài", "B" ));
        questions.add(new Question(104, "Hãy chọn câu trả lời đúng: Một người ngồi trên đoàn tàu đang chạy thấy nhà cửa bên đường chuyển động. Khi ấy người đó đã chọn vật mốc là:", "Toa tầu.", "Bầu trời.", "Cây bên đường.", "Đường ray.", "A" ));
        questions.add(new Question(105, "Một ô tô đỗ trong bến xe, trong các vật mốc sau đây, vật mốc nào thì ô tô xem là chuyển động? Hãy chọn câu đúng:", "Bến xe", "Một ô tô khác đang rời bến", "Một ô tô khác đang đậu trong bến     ", "Cột điện trước bến xe", "B" ));
        questions.add(new Question(106, "Khi nói Mặt Trăng quay quanh Trái Đất, vật chọn làm mốc là:", "Trái Đất", "Mặt Trăng", "Một vật trên Mặt Trăng", "Một vật trên Trái Đất", "A" ));
        questions.add(new Question(107, "Đại lượng nào sau đây cho biết mức độ nhanh hay chậm của chuyển động?", "Quãng đường.", "Thời gian chuyển động.", "Vận tốc.", "Cả 3 đại lượng trên.", "C" ));
        questions.add(new Question(108, "Công thức tính vận tốc là:", "V = t/s", "V=s/t", "V=s.t", "V=m/s", "B" ));
        questions.add(new Question(109, "Chọn đáp án đúng: Vận tốc phụ thuộc vào", "quãng đường chuyển động.", "thời gian chuyển động.", "cả A và B đúng.", "cả A và B sai", "C" ));
        questions.add(new Question(110, "Có mấy loại lực ma sát?", "1", "2", "3", "4", "C" ));
        questions.add(new Question(111, "Khi đoàn tàu đang chuyển động trên đường nằm ngang thì áp lực có độ lớn bằng lực nào?", "Lực kéo do đầu tàu tác dụng lên toa tàu", "Trọng lực của tàu", "Lực ma sát giữa tàu và đường ray", "Cả ba lực trên", "B" ));
        questions.add(new Question(112, "Đơn vị của áp lực là:", "N/m2", "Pa", "N", "N/cm2", "C" ));
        questions.add(new Question(113, "Khi nhúng một khối lập phương vào nước, mặt nào của khối lập phương chịu áp lực lớn nhất của nước?", "Áp lực như nhau ở cả 6 mặt", "Mặt trên", "Mặt dưới", "Các mặt bên", "C" ));
        questions.add(new Question(114, "Công thức tính áp suất gây ra bởi chất lỏng có trọng lượng riêng d tại một điểm cách mặt thoáng có độ cao h là:", "p = d.h", "p = h/d", "p = d/h", "Một công thức khác", "A" ));
        questions.add(new Question(115, "Kangaroo sử dụng bộ phận nào để cân bằng?", "2 Chân", "Đuôi", "Ngực", "Đầu", "B" ));
        questions.add(new Question(116, "Một cục nước đá đang nổi trong bình nước. Mực nước trong bình thay đổi như thế nào khi cục nước đá tan hết:", "Tăng", "Giảm", "Không đổi", "Không xác định được", "C" ));
        questions.add(new Question(117, "Một vật ở trong nước chịu tác dụng của những lực nào?", "Lực đẩy Acsimét", "Lực đẩy Acsimét và lực ma sát", "Trọng lực", "Trọng lực và lực đẩy Acsimét", "D" ));
        questions.add(new Question(118, "Công thức tính lực đẩy Acsimet là:", "FA=DV", "FA= Pvat", "FA= dV", "FA= d.h", "C" ));
        questions.add(new Question(119, "Móc 1 quả nặng vào lực kế ở ngoài không khí, lực kế chỉ 30N. Nhúng chìm quả nặng đó vào trong nước số chỉ của lực kế thay đổi như thế nào?", "Tăng lên", "Giảm đi", "Không thay đổi", "Chỉ số 0.", "B" ));
        questions.add(new Question(120, "Một người đi xe đạp đi đều từ chân dốc lên đỉnh dốc cao 5m. Dốc dài 40m, biết lực ma sát cản trở xe chuyển động trên mặt đường là 20N và cả người cùng xe có khối lượng 37,5kg. Công tổng cộng do người đó sinh ra là bao nhiêu?", "A=3800J", "A=4200J", "A=4000J", "Một giá trị khác", "D" ));
        questions.add(new Question(121, "Vật có cơ năng khi:", "Vật có khả năng sinh công", "Vật có khối lượng lớn", "Vật có tính ì lớn", "Vật có đứng yên", "A" ));
        questions.add(new Question(122, "Thế năng hấp dẫn phụ thuộc vào những yếu tố nào? Chọn câu trả lời đầy đủ nhất.", "Khối lượng", "Trọng lượng riêng", "Khối lượng và vị trí của vật so với mặt đất", "Dạng đường đi của chuyển động", "C" ));
        questions.add(new Question(123, "Thế năng đàn hồi phụ thuộc vào những yếu tố nào?", "Khối lượng", "Độ biến dạng của vật đàn hồi", "Khối lượng và chất làm vật", "Vận tốc của vật", "B" ));
        questions.add(new Question(124, "Nếu chọn mặt đất làm mốc để tính thế năng thì trong các vật sau đây vật nào không có thế năng?", "Viên đạn đang bay", "Lò xo để tự nhiên ở một độ cao so với mặt đất", "Hòn bi đang lăn trên mặt đất", "Lò xo bị ép đặt ngay trên mặt đất", "C" ));
        questions.add(new Question(125, "Lần đầu tiên, Việt Nam được bầu làm Ủy viên không thường trực Hội đồng Bảo an Liên hợp quốc trong nhiệm kì nào?", "2008 - 2009.", "2011 - 2012.", "2018 - 2019", "2021 - 2022.", "A" ));
        questions.add(new Question(126, "Kế hoạch 5 năm (1946-1950) của Liên Xô được tiến hành trong thời gian bao lâu?", "4 năm 3 tháng", "1 năm 3 tháng", "12 tháng", "9 tháng", "A" ));
        questions.add(new Question(127, "Kế hoạch 5 năm (1946-1950) được Liên Xô tiến hành đã hoàn thành trước thời hạn bao lâu?", "1 năm 3 tháng", "9 tháng", "12 tháng", "10 tháng", "B" ));
        questions.add(new Question(128, "Tốc độ tăng trưởng của nền kinh tế Liên Bang Nga từ năm 1991 đến năm 1995 rơi vào tình trạng", "Luôn là con số âm", "Chậm phát triển", "Không phát triển", "Trì trệ, chậm phát triển", "A" ));
        questions.add(new Question(129, "Đà điểu đực được ví tiếng gầm như con gì?", "Hải âu", "Voi", "Sư tử", "Báo", "C" ));
        questions.add(new Question(130, "Kinh tế Nga bắt đầu có những tín hiệu phục hồi từ năm nào?", "Từ năm 1995", "Từ năm 1996", "Từ năm 1997", "Từ năm 1998", "B" ));
        questions.add(new Question(131, "Thể chế chính trị của Liên Bang Nga từ năm 1993 trở đi là", "Cộng hòa Liên Bang", "Cộng hòa Tổng thống", "Tổng thống Liên Bang", "Quân chủ lập hiến", "C" ));
        questions.add(new Question(132, " Sau khi Liên Xô sụp đổ, Liên bang Nga trở thành: ", "Quốc gia độc lập như các nước cộng hòa khác.", "Quốc gia kế tục Liên Xô.", "Quốc gia nắm mọi quyền hành ở Liên Xô.", "Quốc gia Liên bang Xô viết.", "B" ));
        questions.add(new Question(133, "Bản hiến pháp đầu tiên của Liên bang Nga được ban hành vào khoảng thời gian nào?", "Dec-92", "Dec-93", "Feb-93", "Nov-93", "B" ));
        questions.add(new Question(134, "Trước Chiến tranh thế giới thứ hai, tình hình các nước Đông Bắc Á như thế nào?", "Đều bị các nước thực dân xâm lược.", "Đều là những quốc gia độc lập.", "Hầu hết đều bị chủ nghĩa thực dân nô dịch.", "Có nền kinh tế phát triển.", "C" ));
        questions.add(new Question(135, "Các quốc gia và vùng lãnh thổ nào ở khu vực Đông Bắc Á được mệnh danh là “con rồng” kinh tế châu Á?", "Hàn Quốc, Nhật Bản, Hồng Công", "Nhật Bản, Hồng Công, Đài Loan", "Hàn Quốc, Đài Loan, Hồng Công", "Hàn Quốc, Nhật Bản, Đài Loan", "C" ));
        questions.add(new Question(136, "Quốc gia và vùng lãnh thổ nào dưới đây không được mệnh danh là “con rồng” kinh tế của châu Á?", "Hàn Quốc", "Đài Loan", "Hồng Công", "Nhật Bản", "D" ));
        questions.add(new Question(137, "Bán đảo Triều Tiên bị chia cắt thành 2 miền theo vĩ tuyến số bao nhiêu?", "Vĩ tuyến 39", "Vĩ tuyến 38", "Vĩ tuyến 16", "Vĩ tuyến 37", "B" ));
        questions.add(new Question(138, "Trong những năm 1950-1953, hai miền bán đảo Triều Tiên ở trong tình thế: ", "Hòa dịu, hợp tác", "Hòa bình, hòa hợp", "Đối đầu nhưng không xảy ra xung đột quân sự", "Chiến tranh xung đột", "D" ));
        questions.add(new Question(139, "Loài chim có thể bay lùi là:", "Chim Palila ", "Chim Ruồi", "Chim vảy cá", "Chim ô-tit Ấn Độ", "B" ));
        questions.add(new Question(140, "Cuộc nội chiến (1946-1949) ở Trung Quốc trải qua mấy giai đoạn?", "2", "3", "4", "5", "A" ));
        questions.add(new Question(141, "Đâu là bài hát của Sơn Tùng-MTP?", "Lạc vào hư vô", "Lạc vào xứ sở thần tiên", "Lạc vào hang động", "Lạc trôi", "D" ));
        questions.add(new Question(142, "Trong giai đoạn từ tháng 7-1946 đến tháng 6-1947, quân giải phóng Trung Quốc thực hiện chiến lược", "Phòng ngự", "Phòng ngự tích cực", "Phản công", "Thủ hiểm", "B" ));
        questions.add(new Question(143, "Sau giai đoạn từ tháng 7-1946 đến tháng 6-1947, quân giải phóng Trung Quốc thực hiện chiến lược", "Phòng ngự", "Phòng ngự tích cực", "Phản công", "Thủ hiểm", "C" ));
        questions.add(new Question(144, "Trong giai đoạn 1949 - 1959, Trung Quốc thi hành đường lối đối ngoại như thế nào?", "Thụ động, phụ thuộc vào Liên Xô", "Thù địch với nhiều quốc gia", "Nước lớn", "Hòa bình, tích cực ủng hộ phong trào cách mạng thế giới", "D" ));
        questions.add(new Question(145, "Trung Quốc chính thức thiết lập quan hệ ngoại giao với Việt Nam vào thời gian nào?", "1/18/1949", "1/18/1950", "1/18/1951", "1/20/1950", "B" ));
        questions.add(new Question(146, "Có bao nhiêu bộ mã có chưa nu loại A?", "25", "27", "37", "41", "C" ));
        questions.add(new Question(147, "Các kì nào của nguyên phân, NST ở trạng thái kép?", "Kì giữa, kì sau.", "Kì sau, kì cuối", "Cuối kì trung gian, kì đầu, kì giữa.", "Kì dầu, kì giữa", "C" ));
        questions.add(new Question(148, "Trong cấu trúc siêu hiển vi của NST nhân thực, sợi cơ bản có đường kính bằng:", "2nm", "11nm", "20nm", "30nm", "B" ));
        questions.add(new Question(149, "Thành phần chủ yếu trong các cơ thể sống là:", "chất hữu cơ", "chất vô cơ", "nước", "vitamin", "C" ));
        questions.add(new Question(150, "Giả sử một gen được cấu tạo từ 3 loại nuclêôtit: A, T, G thì trên mạch gốc của gen này có thể có tối đa bao nhiêu loại mã bộ ba?", "3 loại mã bộ ba.", "27 loại mã bộ ba.", "27 loại mã bộ ba.", "9 loại mã bộ ba.", "C" ));
        questions.add(new Question(151, "Ở sinh vật nhân thực, trình tự nuclêôtit trong vùng mã hóa của gen nhưng không mã hóa axit amin được gọi là", "đoạn intron.", "đoạn êxôn.", "gen phân mảnh.", "vùng vận hành.", "A" ));
        questions.add(new Question(152, "Trong 64 bộ ba mã di truyền, có 3 bộ ba không mã hoá cho axit amin nào. Các bộ ba đó là:", "UGU, UAA, UAG", "UUG, UGA, UAG", "UAG, UAA, UGA", "UUG, UAA, UGA", "C" ));
        questions.add(new Question(153, "Trong cơ chế điều hòa hoạt động của opêron Lac ở E.coli, lactôzơ đóng vai trò của chất", "xúc tác", "ức chế", "cảm ứng", "trung gian", "C" ));
        questions.add(new Question(154, "Sư phụ đầu tiên của Tôn Ngộ Không là ai?", "Bồ tát", "Phật tổ", "Bồ Đề tổ sư", "Đường Tăng", "C" ));
        questions.add(new Question(155, "Trong cấu trúc của một opêron Lac, nằm ngay trước vùng mã hóa các gen cấu trúc là", "vùng điều hoà", "vùng vận hành", "vùng khởi động", "gen điều hoà", "B" ));
        questions.add(new Question(156, "Bóp nát quả __", "Quýt", "Sầu Riêng", "Cam", "Xoài", "C" ));
        questions.add(new Question(157, "1+1*2=?", "1", "2", "3", "4", "C" ));
        questions.add(new Question(158, "Mạch gốc của gen ban đầu: 3’ TAX TTX AAA... 5’. Cho biết có bao nhiêu trường hợp thay thế nuclêôtit ở vị trí số 7 làm thay đổi codon này thành codon khác?", "", "", "", "", "" ));
        questions.add(new Question(159, "Tác nhân sinh học có thể gây đột biến gen là", "vi khuẩn", "động vật nguyên sinh", "5BU", "virut hecpet", "D" ));
        questions.add(new Question(160, "Gen ban đầu có cặp nuclêôtit chứa A hiếm (A*) là T-A* , sau đột biến cặp này sẽ biến đổi thành cặp:", "T-A", "A-T", "G-X", "X-G", "D" ));
        questions.add(new Question(161, "Trư bát giới có bao nhiêu phép thần thông? ", "72", "79", "39", "36", "D" ));
        questions.add(new Question(162, "Xét đột biến gen do 5BU, thì từ dạng tiền đột biến đến khi xuất hiện gen đột biến phải qua", "1 lần nhân đôi.", "2 lần nhân đôi.", "3 lần nhân đôi.", "4 lần nhân đôi.", "B" ));
        questions.add(new Question(163, "1+1=?", "2", "3", "em", "Một ô cửa sổ", "2" ));
        questions.add(new Question(164, "Biến đổi trên một cặp nuclêôtit của gen phát sinh trong nhân đôi ADN được gọi là", "đột biến", "đột biến gen.", "thể đột biến", "đột biến điểm", "D" ));
        questions.add(new Question(165, "Gen cấu trúc của vi khuẩn có đặc điểm gì?", "Phân mảnh.", "Vùng mã hóa không liên tục.", "Không phân mảnh.", "Không mã hóa axit amin mở đầu.", "C" ));
        questions.add(new Question(166, "Có bao nhiêu điều Bác Hồ dạy thiếu niên, nhi đồng", "1", "2", "3", "5", "D" ));
        questions.add(new Question(167, "Bản hiến pháp đầu tiên của Liên bang Nga được ban hành vào khoảng thời gian nào?", "Dec-92", "Dec-93", "Feb-93", "Nov-93", "B" ));
        questions.add(new Question(168, "Theo nội dung của thuyết electron, phát biểu nào sau đây là sai?", "Electron có thể rời khỏi nguyên tử để di chuyển từ nơi này đến nơi khác", "Vật nhiễm điện âm khi chỉ số electron mà nó chứa lớn hơn số proton", "Nguyên tử nhận thêm electron sẽ trở thành ion dương", "Nguyên tử bị mất electron sẽ trở thành ion dương", "C" ));
        questions.add(new Question(169, "Hội nghị Ianta (1945) có sự tham gia của các nước nào?", "Anh - Pháp - Mĩ.", "Anh - Mĩ - Liên Xô.", "Anh - Pháp - Đức", "Mĩ - Liên Xô - Trung Quốc.", "B" ));
        questions.add(new Question(170, "Có bao nhiêu điều Bác Hồ đã dạy", "", "", "", "", "" ));
        questions.add(new Question(171, "Theo quyết định của hội nghị Ianta (2-1945), quốc gia nào cần phải trở thành một quốc gia thống nhất và dân chủ?", "Đức", "Mông Cổ", "Trung Quốc", "Triều Tiên", "C" ));
        questions.add(new Question(172, "Đâu không phải là tên, bí danh và bút danh của Chủ tịch Hồ Chí Minh", "Nguyễn Sinh Cung", "Henri Tchen", "Nguyễn Văn Thành", "Chen Veng", "D" ));
        questions.add(new Question(173, "Theo quy định của Hội nghị Ianta (2-1945), quốc gia nào sẽ thực hiện nhiệm vụ chiếm đóng, giải giáp miền Tây Đức, Tây Béc-lin và các nước Tây Âu?", "Liên Xô", "Mĩ", "Mĩ, Anh", "Mĩ, Anh, Pháp", "D" ));
        questions.add(new Question(174, "Tôn ngộ không năm xưa từng đại náo ___", "Địa ngục", "Thiên cung", "Trần gian", "Vũ trụ", "B" ));
        questions.add(new Question(175, "Hiện tượng cộng hưởng cơ được ứng dụng trong:", "máy đầm nền.", "giảm xóc ô tô, xe máy.", "con lắc đồng hồ.", "con lắc vật lý.", "A" ));
        questions.add(new Question(176, "Trước Chiến tranh thế Giới thứ II, Inđônêxia là thuộc địa của nước nào?", "Anh", "Mĩ", "Hà Lan", "Pháp", "C" ));
        questions.add(new Question(177, "Hello là gì?", "Xin chào", "Tạm biệt", "Cảm ơn", "Thần kì", "A" ));
        questions.add(new Question(178, "Mặt trăng xoay quanh __", "Mặt trời", "Trái Đất", "Sao Hỏa", "Sao Kim", "B" ));
        questions.add(new Question(179, "Tên đầy đủ của người thầy lấy kinh trong bộ phim Tây du kí là ai? ", "Huyền Trang", "Đường Tam Tạng", "Trần Huyền Trang", "Trần Huy", "A" ));
        questions.add(new Question(180, "Sau khi Nhật đầu hàng Đồng minh, 3 nước ở Đông Nam Á tuyên bố độc lập là:", "Việt Nam, Philippin, Lào.", "Philippin, Lào, Việt Nam.", "Inđonêxia, Việt Nam, Lào.", "Miến Điện, Lào, Việt Nam.", "C" ));
        questions.add(new Question(181, "Nước nào dưới đây tuyên bố độc lập và thành lập chế độ cộng hoà sớm nhất ở Đông Nam Á ?", "Việt Nam", "Malaixia.", "Miến Điện.", "Inđônêxia.", "D" ));
        questions.add(new Question(182, "Trước năm 1959, Singapo là thuộc địa của nước :", "Pháp", "Mĩ", "Hà Lan", "Anh", "D" ));
        questions.add(new Question(183, "Nhân vật nào được mệnh danh là: Người cha của đất nước Singapo hiện đại?", "Ápdun Raman.", "Lí Quang Diệu.", "Lí Thừa Vãn.", "Chu Dung Cơ.", "B" ));
        questions.add(new Question(184, "Người tiến hành cuộc vận động ngoại giao đòi Pháp trao trả độc lập cho nước Campuchia (6/1952 và 9/1953) là:", "Xihanúc.", "Sơn Ngọc Minh.", "XupHanuvông.", "Nôrốđôm.", "A" ));
        questions.add(new Question(185, "Liên xô phóng thành công vệ tinh nhân tạo của trái đất vào năm nào?", "1955", "1957", "1961", "1963", "B" ));
        questions.add(new Question(186, "Vào khoảng thời gian nào các nước Đông Âu lần lượt hoàn thành cách mạng dân chủ nhân dân và bước vào thời kỳ xây dựng Chủ nghĩa xã hội?", "1945-1946.", "1946-1947.", "1947-1948.", "1948-1949.", "D" ));
        questions.add(new Question(187, "Năm 1973 diễn ra sự kiện gì có ảnh hưởng rất lớn đối với các nước?", "Khủng hoảng kinh tế.", "Khủng hoảng năng lượng.", "Khủng hoảng chính trị.", "Tất cả các sự kiện trên.", "B" ));
        questions.add(new Question(188, "Hội đồng tương trợ kinh tế (SEV) được thành lập vào thời gian nào?", "8-1-1949", "1-8-1949", "18-1-1950", "14-5-1955", "A" ));
        questions.add(new Question(189, "Hiệp ước Vác-sa-va được thành lập vào thời gian nào?", "8-1-1949", "14-5-1955", "15-4-1955", "16-7-1954", "B" ));
        questions.add(new Question(190, "Tổ chức hiệp ước Vác-sa-va trở thành một đối trọng với khối quân sự nào của Mĩ ?", "SEATO", "CENTO", "NATO", "ANZUSS", "C" ));
        questions.add(new Question(191, "Hiệp ước hữu nghị liên minh tương trợ Xô-Trung được ki kết vào thời gian nào?", "1-10-1949", "14-2-1950", "12-4-1950", "16-12-1949", "B" ));
        questions.add(new Question(192, "Từ sau Chiến tranh thế giới hai đến nay, thế giới tồn tại bao nhiêu trung tâm kinh tế tài chính?", "2", "3", "4", "5", "C" ));
        questions.add(new Question(193, "Nước nào khởi đầu cuộc cách mạng khoa học- kĩ thuật lần thứ hai?", "Anh", "Pháp", "Mĩ", "Nhật", "C" ));
        questions.add(new Question(194, "Nước nào đưa con người lên Mặt Trăng đầu tiên(7/1969)?", "Mĩ", "Nhật", "Liên Xô", "Trung Quốc", "A" ));
        questions.add(new Question(195, "Trong các liên minh quân sự dưới đây, liên minh nào không phải do Mĩ lập nên?", "NATO", "VACSAVA", "SEATO ", "Cả ba khối trên", "B" ));
        questions.add(new Question(196, "Chính sách thực lực và “Chiến lược toàn cầu của đế quốc Mĩ bị thất bại nặng nề nhất ở đâu?", "Triều Tiên", "Việt Nam", " Cu-ba", "I-rắc", "B" ));
        questions.add(new Question(197, "năm 1950-1973, nước Tây Âu nào đã thực hiện đường lối đối ngoại độc lập với Mĩ?", "Anh", " Pháp", "Italia", "Cộng hoà Liên bang Đức", "B" ));
        questions.add(new Question(198, "Nước nào dưới đây đã từng ủng hộ cuộc chiến tranh của Mĩ ở Việt Nam ?", "VAnh", "Pháp", "Thuỵ Điển", " Phần Lan", "A" ));
        questions.add(new Question(199, "German mang nghĩa gì trong các đáp án sau:", "Đức", "Pháp", "Tây Ban Nha", "Anh", "A" ));
        questions.add(new Question(200, "Trong quan hệ đối ngoại hiện nay, quốc gia nào ở Tây Âu là nước duy nhất còn duy trì liên minh chặt chẽ với Mĩ?", "Pháp.", "Anh.", "Italia.", "Đức.", "B" ));

    }

    //function tra loi
    public static boolean CheckQuestion(Question question){

        for(Question item:plays){
            if(item.getQuestionId()==question.getQuestionId())
                return true;
        }
        return false;
    }
    //function Continue
    public void Continue(){
        if(current==10){
            Toast.makeText(MainActivity.this, "Chúc mừng bạn đã chiến thắng "+ String.valueOf(current)+"/10", Toast.LENGTH_SHORT).show();
        }else{
            prb_time.setProgress(59);
            countDownTimer = new CountDownTimer(61000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int current = prb_time.getProgress();
                    prb_time.setProgress(current-1);
                    tv_second.setText(String.valueOf(current));
                }

                @Override
                public void onFinish() {
                    Toast.makeText(MainActivity.this, "Hết thời gian", Toast.LENGTH_SHORT).show();
                    //ko lua chon
                    if(rdoA.isChecked()==false && rdoB.isChecked()==false && rdoC.isChecked()==false && rdoD.isChecked()==false){
                        Toast.makeText(MainActivity.this, "Số điểm của bạn là: " + String.valueOf(current)+"/10", Toast.LENGTH_SHORT).show();
                        //co cau tra loi, chia lam 2 truong hop
                    }else{
                        if(rdoA.isChecked())
                            choose="A";
                        else if(rdoB.isChecked())
                            choose="B";
                        else if(rdoC.isChecked())
                            choose="C";
                        else
                            choose="D";
                        //chon dung
                        if(plays.get(current).getCorrectAnswer().equals(choose)){
                            Question question = new Question();
                            int newQuestion=-1;
                            do{
                                newQuestion = rand.nextInt(200);
                                question = questions.get(newQuestion);
                            }while (CheckQuestion(question));
                            current++;
                            if(current<10){
                                //add new questions
                                plays.add(question);
                                tv_title.setText("Câu hỏi thứ "+String.valueOf(current+1));
                                tv_content.setText(plays.get(current).getContent());
                                rdoA.setText("A. "+ plays.get(current).getAnswerA());
                                rdoB.setText("B. "+ plays.get(current).getAnswerB());
                                rdoC.setText("C. "+ plays.get(current).getAnswerC());
                                rdoD.setText("D. "+ plays.get(current).getAnswerD());
                                choose="";
                                rdoA.setChecked(false);
                                rdoB.setChecked(false);
                                rdoC.setChecked(false);
                                rdoD.setChecked(false);
                                //run-time again
                                Continue();
                            } else{
                                Toast.makeText(MainActivity.this, "Bạn đã chiến thắng trả lời "+String.valueOf(current)+"/10", Toast.LENGTH_SHORT).show();
                            }
                        //chon sai
                        }else{
                            Toast.makeText(MainActivity.this, "Số điểm của bạn là: " + String.valueOf(current)+"/10", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }; countDownTimer.start();
        }
    }
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mapping
        tv_title=(TextView) findViewById(R.id.tv_title);
        tv_second=(TextView) findViewById(R.id.tv_second);
        tv_content=(TextView) findViewById(R.id.tv_content);
        prb_time=(ProgressBar) findViewById(R.id.prb_time);
        rdoA=(RadioButton) findViewById(R.id.rdoA);
        rdoB=(RadioButton) findViewById(R.id.rdoB);
        rdoC=(RadioButton) findViewById(R.id.rdoC);
        rdoD=(RadioButton) findViewById(R.id.rdoD);
        btn_answer=(Button) findViewById(R.id.btn_answer);
        btn_exit=(Button) findViewById(R.id.btn_exit);
        //Init();
        ////////////////////////////////////////////////
//        //Cach 1: Create Db class
//        database = new Database(this,"mydb.sqlite", null, 1);
//        database.ExecNonQuery("CREATE TABLE IF NOT EXISTS QUESTION(QUESTIONID INT PRIMARY KEY, CONTENT TEXT, answerA TEXT, answerB TEXT, answerC TEXT, answerD TEXT, correctAnswer TEXT)");
//        String pathdb = getDatabasePath("mydb.sqlite").getPath();
//        tv_title.setText(pathdb);
//        for(Question item:questions){
//            String sql ="insert into QUESTION values("+String.valueOf(item.getQuestionId())+",'"+item.getContent()+"','"+item.getAnswerA()+"','"+item.getAnswerB()+"','"+item.getAnswerC()+"','"+item.getAnswerD()+"','"+item.getCorrectAnswer()+"')";
//            database.ExecNonQuery(sql);
//        }
        //xoa csdl
        //deleteDatabase("mydb.sqlite");
        ///////////////////////////////////
        //cach 2:
        // create db
        SQLiteDatabase db = openOrCreateDatabase("mydb.sqlite", Context.MODE_PRIVATE, null);
        //create table
        db.execSQL("CREATE TABLE IF NOT EXISTS QUESTION(QUESTIONID INT PRIMARY KEY, CONTENT TEXT, answerA TEXT, answerB TEXT, answerC TEXT, answerD TEXT, correctAnswer TEXT)");
//        String pathdb = getDatabasePath("mydb.sqlite").getPath();
//        Toast.makeText(MainActivity.this, pathdb, Toast.LENGTH_SHORT).show();
//        tv_title.setText(pathdb);
        //delete data
        db.execSQL("delete from question");
        String sql1="select * from Question";
        Cursor data = db.rawQuery(sql1,null);
        int num=data.getCount();  //size of cursor
        //table is empty
//        if (num==0){
//            for(Question item:questions){
//                String sql ="insert into QUESTION values("+String.valueOf(item.getQuestionId())+",'"+item.getContent()+"','"+item.getAnswerA()+"','"+item.getAnswerB()+"','"+item.getAnswerC()+"','"+item.getAnswerD()+"','"+item.getCorrectAnswer()+"')";
//                db.execSQL(sql);
//            }
//        }
        questions=new ArrayList<>();
        //clear list of question
//        questions.clear();
        // Load data from db to list of question
        String query="select * from Question";
        Cursor result=db.rawQuery(query,null);
        while (result.moveToNext()){
            Question newQuestion=new Question();
            newQuestion.setQuestionId(result.getInt(0));
            newQuestion.setContent(result.getString(1));
            newQuestion.setAnswerA(result.getString(2));
            newQuestion.setAnswerB(result.getString(3));
            newQuestion.setAnswerC(result.getString(4));
            newQuestion.setAnswerD(result.getString(5));
            newQuestion.setCorrectAnswer(result.getString(6));
            questions.add(newQuestion);
            Toast.makeText(MainActivity.this, newQuestion.getContent(), Toast.LENGTH_SHORT).show();
        }

        //chay trong 60s, delay 1s -> 61000 se chay v 0
        CountDownTimer countDownTimer= new CountDownTimer(61000, 1000) {
            @Override

            //trong qt chay, goi ham onclick
            public void onTick(long millisUntilFinished) {
                //code here
                int current = prb_time.getProgress();
                prb_time.setProgress(current-1);

                //setText cho 60s
                tv_second.setText(String.valueOf(current));
            }
            //het gio
            @Override
            public void onFinish() {
                // code here
                Toast.makeText(MainActivity.this, "Hết thời gian", Toast.LENGTH_SHORT).show();
                // ko lua chon dap ap nao
                if(rdoA.isChecked()==false && rdoB.isChecked()==false && rdoC.isChecked()==false && rdoD.isChecked()==false){
                    Toast.makeText(MainActivity.this, "Số điểm của bạn là: " + String.valueOf(current)+"/10", Toast.LENGTH_SHORT).show();
                    //co cau tra loi, chia lam 2 truong hop
                } else{
                    if(rdoA.isChecked())
                        choose="A";
                    else if(rdoB.isChecked())
                        choose="B";
                    else if(rdoC.isChecked())
                        choose="C";
                    else
                        choose="D";
                    //chon dung
                    if(plays.get(current).getCorrectAnswer().equals(choose)){
                        Question question = new Question();
                        int newQuestion=-1;
                        do{
                            newQuestion = rand.nextInt(200);
                            question = questions.get(newQuestion);
                        }while (CheckQuestion(question));
                        current++;
                        //add new questions
                        plays.add(question);
                        tv_title.setText("Câu hỏi thứ "+String.valueOf(current+1));
                        tv_content.setText(plays.get(current).getContent());
                        rdoA.setText("A. "+ plays.get(current).getAnswerA());
                        rdoB.setText("B. "+ plays.get(current).getAnswerB());
                        rdoC.setText("C. "+ plays.get(current).getAnswerC());
                        rdoD.setText("D. "+ plays.get(current).getAnswerD());
                        choose="";
                        rdoA.setChecked(false);
                        rdoB.setChecked(false);
                        rdoC.setChecked(false);
                        rdoD.setChecked(false);
                        //run-time again
                        Continue();

                    }else{
                        //chon sai
                        finish();
                        Toast.makeText(MainActivity.this, "Số điểm của bạn là: " + String.valueOf(current)+"/10", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        };
        countDownTimer.start();
        Init();
        plays = new ArrayList<>();
        rand= new Random();
        int index = rand.nextInt(questions.size()); //random 0 - 199
        plays.add(questions.get(index));
        current = 0;
        choose = "";
        //set data cho question va cac cau tra loi cua dap an
        tv_content.setText(plays.get(0).getContent());
        rdoA.setText("A. "+plays.get(0).getAnswerA());
        rdoB.setText("B. "+plays.get(0).getAnswerB());
        rdoC.setText("C. "+plays.get(0).getAnswerC());
        rdoD.setText("D. "+plays.get(0).getAnswerD());
        Continue();

        //thoat
//        public void finishQuestion(){
//            Intent resultIntent = new Intent();
//            setResult(RESULT_OK, resultIntent);
//            finish();
//        }
        //function tiep tuc
        btn_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code here
                if(rdoA.isChecked()==false && rdoB.isChecked()==false && rdoC.isChecked()==false && rdoD.isChecked()==false){
                    Toast.makeText(MainActivity.this, "Số điểm của bạn là "+ String.valueOf(current)+"/10", Toast.LENGTH_SHORT).show();
                } else{
                    if(rdoA.isChecked())
                        choose="A";
                    else if(rdoB.isChecked())
                        choose="B";
                    else if(rdoC.isChecked())
                        choose="C";
                    else
                        choose="D";
                    //chon dung
                    if(plays.get(current).getCorrectAnswer().equals(choose)){
                        Question question = new Question();
                        int newQuestion=-1;
                        do{
                            newQuestion=rand.nextInt(200);
                            question=questions.get(newQuestion);
                        }while (CheckQuestion(question));
                        current++;
                        if(current==10){
                            Toast.makeText(MainActivity.this, "Bạn đã chiến thắng, số điểm bạn là "+String.valueOf(current)+"/10", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        plays.add(question);
                        tv_title.setText("Câu hỏi thứ "+String.valueOf(current+1));
                        tv_content.setText(plays.get(current).getContent());
                        rdoA.setText("A. "+plays.get(current).getAnswerA());
                        rdoB.setText("B. "+plays.get(current).getAnswerB());
                        rdoC.setText("C. "+plays.get(current).getAnswerC());
                        rdoD.setText("D. "+plays.get(current).getAnswerD());
                        choose="";
                        rdoA.setChecked(false);
                        rdoB.setChecked(false);
                        rdoC.setChecked(false);
                        rdoD.setChecked(false);
                        countDownTimer.cancel();
                        //chay lai dong ho
                        Continue();
                    }else{
                        Toast.makeText(MainActivity.this, "Số điểm của bạn là: "+String.valueOf(current)+"/10", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        ///////////////////////////////////////
        //thoat function
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Warning!!!");
                alertDialogBuilder.setMessage("Are u sure, You want to exit?");
                alertDialogBuilder.setCancelable(false);
                //Yes
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // reset time ko anh huong khi bam vao btn thoat
                        countDownTimer.cancel();
                        finish();
                    }
                });
                //No
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Ok, fine!", Toast.LENGTH_SHORT).show();
                    }
                });
                //cancel
//                alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MainActivity.this, "Ok Cancel", Toast.LENGTH_SHORT).show();
//                    }
//                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }
}