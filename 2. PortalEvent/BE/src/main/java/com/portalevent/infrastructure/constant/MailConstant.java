package com.portalevent.infrastructure.constant;

public class MailConstant {
    public static final String LOGO_PATH = "/static/assets/images/logo_udpm.png";
    public static final String LOGO_PATH2 = "/static/assets/images/logo_udpm2.png";
    public static final String SUBJECT = "[Portal Event][PTPM-XLDL-UDPM] ";

    public static final String HEADER = """
            <!DOCTYPE hmtl>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
                <style>
                    .container {
                        max-width: 1140px;
                        margin: 0 auto;    
                        font-size: 16px;               
                    }

                    .bg-secondary {
                        background-color: #6c757d !important;
                        color: #fff !important;
                    }

                    .border {
                        border: 1px solid #dee2e6 !important;
                    }

                    .border-2 {
                        border-width: 2px !important;
                    }

                    .text-center {
                        text-align: center !important;
                    }

                    .small {
                        font-size: 0.875rem !important;
                    }

                    .list-unstyled {
                        padding-left: 0;
                        list-style: none;
                    }

                    .border-bottom {
                        border-bottom: 3px solid #ddd;
                    }
                    .border-top {
                        border-top: 3px solid #ddd;
                    }
                    table {
                        border-collapse: collapse; 
                        width: 100%;
                    }
                    th, td {
                        border: 1px solid #c9cbcd; 
                        padding: 8px;
                    }        
                </style>
            </head>

            <body>
                <div class="container">
                    <div class=" text-center border-bottom" style="background-color: #132A4D;">
                        <img src="cid:logoImage" height="80px">
                    </div>
                    <div class="container" style="margin: 10px 10px;">
                    <div style="text-align: center"><h3>{title}</h3></div>
                        """;

    public static final String CONTENT_EVENT_MAIL = """
            <ul class="list-unstyled"">
                        <li>Thân gửi các bạn sinh viên,</li>
                        <li>Đầu tiên xin chúc mừng các bạn đã đăng ký thành công cho <strong style="text-transform: uppercase;">{eventName}</strong> với sự tham gia của các giảng viên:
                            <ul>
                                {lecturerList}
                            </ul>
                            <br>
                            <ol>
                                <li>
                                    <strong>Nội dung bao gồm:</strong>
                                    <ul type="circle">
                                        {contentList}
                                    </ul>
                                </li>
                                <li>
                                    <strong>Thông tin diễn ra:</strong>
                                    <ul type="circle">
                                        <li>Thời gian: {time}</li>
                                        <li>Ngày: {date}</li>
                                        <li>Hình thức: {typeEvent}</li>
                                        <li>Link Zoom: <a href="{linkZoom}">Tại đây</a></li>
                                        <li>Link Background: <a href="{linkBackground}">Tại đây</a></li>
                                    </ul>
                                </li>
                            </ol>
                        </li>
                        <br>
                        <li>
                            Mong nhận được nhiều sự góp ý hơn của các bạn.
                        </li>
                       </ul>
            """;

    public static final String CONTENT_APPROVAL_EVENT_MAIL = """
                                           <ul class="list-unstyled">
                                               <li>Thân gửi các thầy cô,</li>
                                               <li>Thông tin của sự kiện <strong
                                                       style="text-transform: uppercase;">{eventName}</strong>\s
                                                   <br>
                                                   <ol>
                                                       <li>
                                                           <strong>GV tham gia:</strong>
                                                           <ul type="circle">
                                                               {lecturerList}
                                                           </ul>
                                                       </li>
                                                       <li>
                                                           <strong>Nội dung bao gồm:</strong>
                                                           <ul type="circle">
                                                               {contentList}
                                                           </ul>
                                                       </li>
                                                       <li>
                                                           <strong>Thông tin diễn ra:</strong>
                                                           <ul type="circle">
                                                               <li>Thể loại: {category}</li>
                                                               <li>Sự kiện cho: {typeEvent}</li>
                                                               {date}
                                                               <li>Dự kiến số người tham gia: {expectedParticipants}</li>
                                                               <li>Đối tượng:
                                                                   <ul>
                                                                       {objects}
                                                                   </ul>
                                                               </li>
                                                               <li>Địa điểm:
                                                                   <ul>
                                                                       {locations}
                                                                   </ul>
                                                               </li>
                                                               <li>Tài nguyên:
                                                                   <ul>
                                                                       {resources}
                                                                   </ul>
                                                               </li>
                                                           </ul>
                                                       </li>
                                                       <li>
                                                           <strong>Link thiết kế:</strong>
                                                           <ul type="circle">
                                                               <li>Link Background: <a href="{linkBackground}">Tại đây</a></li>
                                                               <li>Link Banner: <a href="{linkBanner}">Tại đây</a></li>
                                                           </ul>
                                                       </li>
                                                       <li>
                                                           <strong>Agenda sự kiện:</strong>
                                                           {agendas}             
                                                       </li>
                                                   </ol>
                                               </li>
                                           </ul>
                                           
            """;
    public static final String CONTENT_CLOSE_EVENT_MAIL = """
            <ul class="list-unstyled" style="margin-top: 10px; margin-bottom: 10px; font-size: 20px;">
                            <h3 style="color: green;">Thông báo người tổ chức {organizerName} đã đóng sự kiện <span
                                style="text-transform: uppercase;">{eventName}</span>.
                            </h3>
                            <ol>
                                <li style="font-size: 16px;">
                                    <strong>Thông tin sự kiện:</strong>
                                    <ul type="circle">
                                        <li>Thể loại: {category}</li>
                                        <li>Sự kiện cho: {typeEvent}</li>
                                        <li>Thời gian: {date}</li>
                                        <li>
                                            GV tham gia:
                                            <ul type="circle">
                                                {lecturerList}
                                            </ul>
                                        </li>
                                        <li>Đối tượng:
                                            <ul>
                                                {objects}
                                            </ul>
                                        </li>
                                    </ul>
                                </li>
                                <li style="font-size: 16px;"><strong>Lý do đóng:</strong> <strong style="color: red;">{reason}</strong></li>
                            </ol>
                        </ul>      
            """;
    public static final String CONTENT_ADD_ORGANIZER_MAIL = """
            <ul class="list-unstyled" style="margin-top: 10px; margin-bottom: 10px; font-size: 20px;">
                <h3 style="color: green;">Người tổ chức {userNameAdd} vừa {status} người tổ chức của sự
                    kiện <span style="text-transform: uppercase;">{eventName}</span> vai trò <span>{role}</span>.
                </h3>
                <li style="font-size: 16px;">Thông tin sự kiện:
                    <ul type="circle">
                        <li>Thể loại: {category}</li>
                        <li>Sự kiện cho: {typeEvent}</li>
                        <li>Thời gian: {date}</li>
                        <li>
                            GV tham gia:
                            <ul type="circle">
                                {lecturerList}
                            </ul>
                        </li>
                        <li>Đối tượng:
                            <ul>
                                {objects}
                            </ul>
                        </li>
                    </ul>
                </li>
            </ul>  
            """;
    public static final String CONTENT_DELETE_ORGANIZER_MAIL = """
            <ul class="list-unstyled" style="margin-top: 10px; margin-bottom: 10px; font-size: 20px;">
                <h3 style="color: green;">Người tổ chức {userNameAdd} xóa bạn khỏi danh sách người tổ chức của sự
                    kiện <span style="text-transform: uppercase;">{eventName}</span>.
                </h3>
                <li style="font-size: 16px;">Thông tin sự kiện:
                    <ul type="circle">
                        <li>Thể loại: {category}</li>
                        <li>Sự kiện cho: {typeEvent}</li>
                        <li>Thời gian: {date}</li>
                        <li>
                            GV tham gia:
                            <ul type="circle">
                                {lecturerList}
                            </ul>
                        </li>
                        <li>Đối tượng:
                            <ul>
                                {objects}
                            </ul>
                        </li>
                    </ul>
                </li>
            </ul>
            """;

    public static final String CONTENT_UPDATE_IMAGES_MAIL = """
                                                <ul class="list-unstyled" style="margin-top: 10px; margin-bottom: 10px; font-size: 20px;">
                                                                <h3 style="color: green;"> Thông báo chỉnh sửa ảnh cần thiết kế của sự kiện <strong
                                                       style="text-transform: uppercase;">{eventName}</strong> ({startDate} - {endDate})
                                                                </h3>
                                                                <ol>
                                                                    <li style="font-size: 16px;">
                                                                        <strong>Link thiết kế:</strong>
                                                                        <ul>
                                                                           <li>{linkBackground}
                                                                           <li>{linkBanner}
                                                                           <li>{linkStandee}
                                                                        </ul>
                                                                    </li>
                                                                </ol>   
                                                            </ul>           
            """;

    public static final String CONTENT_UPDATE_LOCATION_MAIL = """
                                                <ul class="list-unstyled" style="margin-top: 10px; margin-bottom: 10px; font-size: 20px;">
                                                                <h3 style="color: green;"> Thông báo chỉnh sửa địa điểm của sự kiện <strong
                                                       style="text-transform: uppercase;">{eventName}</strong> ({startDate} - {endDate}) </h3>
                                                                <ol>
                                                                    <li style="font-size: 16px;">
                                                                        <strong>Chỉnh sửa địa điểm:</strong>
                                                                        <ul>
                                                                            <li>
                                                                                Sửa:
                                                                                {oldformalityLocation} - {oldLocation}: {oldpathLocation}
                                                                                <=>
                                                                                    {formalityLocation} - {newLocation}: {pathLocation}
                                                                            </li>
                                                                        </ul>
                                                                    </li>
                                                                    <li style="font-size: 16px;">
                                                                        <strong>Danh sách địa điểm trước đó:</strong>
                                                                        <ul>
                                                                                {locations}
                                                                        </ul>
                                                                    </li>
                                                                </ol>  
                                                            </ul>
            """;

    public static final String CONTENT_DELETE_LOCATION_MAIL = """
                                                   <ul class="list-unstyled" style="margin-top: 10px; margin-bottom: 10px; font-size: 20px;">
                                                                   <h3 style="color: green;"> Thông báo chỉnh sửa địa điểm của sự kiện <strong
                                                                           style="text-transform: uppercase;">{eventName}</strong> ({startDate} - {endDate})
                                                                   </h3>
                                                                   <ol>
                                                                       <li style="font-size: 16px;">
                                                                           <strong>Địa điểm vừa xóa:</strong>
                                                                           <ul>
                                                                               <li>
                                                                                   {oldformalityLocation} - {oldLocation}: {oldpathLocation}
                                                                               </li>
                                                                           </ul>
                                                                       </li>
                                                                       <li style="font-size: 16px;">
                                                                           <strong>Danh sách địa điểm hiện tại:</strong>
                                                                           <ul>
                                                                                   {locations}
                                                                           </ul>
                                                                       </li>
                                                                   </ol> 
                                                               </ul>        
            """;

    public static final String CONTENT_ADD_LOCATION_MAIL = """
                                           <ul class="list-unstyled" style="margin-top: 10px; margin-bottom: 10px; font-size: 20px;">
                                                           <h3 style="color: green;"> Thông báo chỉnh sửa địa điểm của sự kiện <strong
                                                       style="text-transform: uppercase;">{eventName}</strong> ({startDate} - {endDate})
                                                           </h3>
                                                           <ol>
                                                               <li style="font-size: 16px;">
                                                                   <strong>Thêm mới địa điểm:</strong>
                                                                   <ul>
                                                                       <li>
                                                                            {formalityLocation} - {newLocation}: {pathLocation}
                                                                       </li>
                                                                   </ul>
                                                               </li>
                                                               <li style="font-size: 16px;">
                                                                   <strong>Danh sách địa điểm hiện tại:</strong>
                                                                   <ul>
                                                                           {locations}
                                                                   </ul>
                                                               </li>
                                                           </ol>  
                                                       </ul>                  
            """;
    public static final String FOOTER = """
                    </div>
                    <div class=" text-center border-top small" style="background-color: #132A4D; padding: 10px 10px;color: #FFF;">
                        <ul class="list-unstyled">
                            <li>Lưu ý : Đây là email tự động vui lòng không phải hồi email này.</li>
                            <li>Mọi thắc mắc xin liên hệ xưởng dự án của Bộ môn Phát Triển Phần Mềm.</li>
                        </ul>
                    </div>
                </div>
            </body>
                  
            </html>
            """;
    public static final String CONTENT_EVENT_EDITING = """
            <h3 style="color: red;">Thông báo từ chối sự kiện "{nameEvent}"</h3>
                        <ul>
                            {timeEvent}
                            <li>Người từ chối: {nameApprove}</li>
                            <li>Lý do: {reason}</li>
                        </ul>
            """;
    public static final String CONTENT_EVENT_APPROVE = """
            <h3 style="color: green;">Thông báo phê duyệt sự kiện "{nameEvent}"</h3>
                        <ul>
                            {timeEvent}
                            <li>Người phê duyệt: {nameApprove}</li>
                        </ul>
            """;
    public static final String CONTENT_EVENT_COMING_SOON = """
            <ul class="list-unstyled">
               <li>Thân gửi các bạn sinh viên,</li>
               <li>Đầu tiên xin chúc mừng các bạn đã đăng ký thành công cho
                <strong style="text-transform: uppercase;">"{eventCategory} - {eventName}"</strong> với sự tham gia của các giảng viên:
                   <br>
                   <ul>
                        {organizer}
                   </ul>
                   <ol>                                                    
                       <li>
                           <strong>Nội dung bao gồm:</strong>
                           <ul type="circle">
                               {contentList}
                           </ul>
                       </li>
                       <li>
                           <strong>Thông tin diễn ra:</strong>
                           <ul type="circle">
                               {time}
                               <li>Địa điểm:
                                   <ul>
                                       {locations}
                                   </ul>
                               </li>
                               {image}
                               {resources}
                           </ul>
                       </li>
                   </ol>
               </li>
           </ul>
           <br/>
           <p>Mong nhận được nhiều sự góp ý hơn của các bạn.</p>		
            """;

    public static final String CONTENT_EVIDENCE_MAIL = """
            <ul class="list-unstyled" style="margin-top: 20px; margin-bottom: 15px;">
                            <li>Thân gửi các thầy cô,</li>
                            <li>Kết quả sau sự kiện <strong
                                    style="text-transform: uppercase;">"{eventName}"</strong> vào {time} ngày {date}
                                <br>
                                <ul class="list-unstyled">
                                    <li>
                                        <ol>
                                            <li>
                                                Số lượng người đăng ký: {countParticipant} người
                                            </li>
                                            <li>
                                                Số lượng người tham dự: khoảng {numberParticipants} người
                                            </li>
                                            <li>
                                                Feedback: 
                                                <ul type="circle">
                                                    <li>Đánh giá chung: {percentage}% người tham dự đã đánh giá buổi {eventType} này 4 hoặc 5 *.</li>
                                                </ul>
                                            </li>
                                        </ol>
                                    </li>
                                    
                                </ul>
                            </li>
                            <li>Kết quả cụ thể và chi tiết:
                                {evidences}  
                            </li>
                        </ul>
            """;

    public static final String CONTENT_RESOURCE_MAIL = """
            <ul class="list-unstyled" style="margin-top: 20px; margin-bottom: 15px;">
                            <li>Thân gửi các bạn sinh viên,</li>
                                    <li>Chúc mừng các bạn đã đăng ký thành công cho <strong
                                        style="text-transform: uppercase;">{category} "{eventName}"</strong> và feedback để cải thiện cho các buổi <strong
                                        style="text-transform: uppercase;">{category}</strong> sắp tới.
                                        <br>
                                        <li>
                                            <strong>Tài liệu sau buổi <strong style="text-transform: uppercase;">{category}</strong>:</strong>
                                            <ul>
                                                {resources}
                                            </ul>
                                        </li>
                                    </li>
                        </ul>
            """;

    public static final String CONTENT_EVENT_UPDATE_WHEN_APPROVED_MAIL = """
            <ul class="list-unstyled">
                            <li>Người tổ chức <b>{userNameChange}</b> đã thay đổi thông tin dự kiện sự kiện <strong
                                    style="text-transform: uppercase;">{eventName} đã được phê duyệt:</strong> 
                                <br>
                                {event}
                            </li>
                        </ul>
            """;

    public static final String CONTENT_ORGANIZER_CHANGE_MAIL = """
            <ul class="list-unstyled" style="margin-top: 10px; margin-bottom: 10px; font-size: 20px;">
                            <h3>
                                {message}
                            </h3>
                            <li>Danh sách người tổ chức:
                                <ul type="circle">
                                    {lecturerList}
                                </ul>
                            </li>
                        </ul>
            """;

    public static final String CONTENT_ANNOUNCE_UPCOMING_EVENT = """
            <ul class="list-unstyled">
                                             <li>Thân gửi các thầy cô,</li>
                                             <li>Thông báo: một sự kiện dành cho giảng viên sắp sửa được tổ chức.
                                                 <br>
                                                 <strong>- Tên sự kiện:{eventCategory} - {eventName}</strong>
                                                 <ol>
                                                     <li>
                                                         <strong>Người tổ chức:</strong>
                                                         <ul type="circle">
                                                             {lecturerList}
                                                         </ul>
                                                     </li>
                                                     <li>
                                                         <strong>Thông tin diễn ra:</strong>
                                                         <ul type="circle">
                                                             {date}
                                                             <li>Địa điểm:
                                                                 <ul>
                                                                     {locations}
                                                                 </ul>
                                                             </li>
                                                             {image}
                                                             {resources}
                                                         </ul>
                                                     </li>
                                                 </ol>
                                             </li>
                                         </ul>
            """;
}
