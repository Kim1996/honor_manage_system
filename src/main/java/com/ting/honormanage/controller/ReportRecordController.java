package com.ting.honormanage.controller;

import com.ting.honormanage.entity.CheckerInfo;
import com.ting.honormanage.entity.HonorInfo;
import com.ting.honormanage.entity.ReportRecord;
import com.ting.honormanage.entity.StudentInfo;
import com.ting.honormanage.model.HonorInfoModel;
import com.ting.honormanage.model.ReportRecordModel;
import com.ting.honormanage.repository.CheckerInfoRepository;
import com.ting.honormanage.repository.HonorInfoRepository;
import com.ting.honormanage.repository.ReportRecordRepository;
import com.ting.honormanage.repository.StudentInfoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nitmali
 */
@RestController
public class ReportRecordController {
    @Resource
    private ReportRecordRepository reportRecordRepository;

    @Resource
    private StudentInfoRepository studentInfoRepository;

    @Resource
    private HonorInfoRepository honorInfoRepository;

    @Resource
    private CheckerInfoRepository checkerInfoRepository;

    @GetMapping("/api/manager_checker/get_reportRecord_all")
    public List<ReportRecordModel> getReportRecordList() {
        List<ReportRecordModel> reportRecordModelArrayList = new ArrayList<>();
        List<ReportRecord> reportRecordList = (List<ReportRecord>) reportRecordRepository.findAll();
        for (int i = 0; i < reportRecordList.size() - 1; i++) {
            ReportRecordModel reportRecordModel = new ReportRecordModel(reportRecordList.get(i));
            reportRecordModelArrayList.add(reportRecordModel);
        }
        return reportRecordModelArrayList;
    }

    @GetMapping("/api/manager_checker/get_reportRecord_id")
    public ReportRecordModel getReportRecordFromId(Long id) {
        return new ReportRecordModel(reportRecordRepository.findReportRecordById(id));
    }

    @GetMapping("/api/manager_checker/get_reportRecord_status")
    public List<ReportRecordModel> getReportRecordListFromStatus(ReportRecord.Status status) {
        List<ReportRecordModel> reportRecordModelArrayList = new ArrayList<>();
        List<ReportRecord> reportRecordList = reportRecordRepository.findReportRecordByStatus(status);
        for (int i = 0; i < reportRecordList.size() - 1; i++) {
            ReportRecordModel reportRecordModel = new ReportRecordModel(reportRecordList.get(i));
            reportRecordModelArrayList.add(reportRecordModel);
        }
        return reportRecordModelArrayList;
    }

    @GetMapping("/api/manager_checker/get_reportRecord_studentInfo")
    public List<ReportRecordModel> getReportRecordListFromStudentInfo(Long studentId) {
        StudentInfo studentInfo = studentInfoRepository.findStudentInfoById(studentId);
        List<ReportRecordModel> reportRecordModelArrayList = new ArrayList<>();
        List<ReportRecord> reportRecordList = reportRecordRepository.findReportRecordByStudentInfo(studentInfo);
        for (int i = 0; i < reportRecordList.size() - 1; i++) {
            ReportRecordModel reportRecordModel = new ReportRecordModel(reportRecordList.get(i));
            reportRecordModelArrayList.add(reportRecordModel);
        }
        return reportRecordModelArrayList;
    }


    @GetMapping("/api/manager_checker/get_reportRecord_honorInfo")
    public List<ReportRecordModel> getReportRecordListFromHonorInfo(Long honorId) {
        HonorInfo honorInfo = honorInfoRepository.findHonorInfoById(honorId);
        List<ReportRecordModel> reportRecordModelArrayList = new ArrayList<>();
        List<ReportRecord> reportRecordList = reportRecordRepository.findReportRecordByHonorInfo(honorInfo);
        for (int i = 0; i < reportRecordList.size() - 1; i++) {
            ReportRecordModel reportRecordModel = new ReportRecordModel(reportRecordList.get(i));
            reportRecordModelArrayList.add(reportRecordModel);
        }
        return reportRecordModelArrayList;
    }

    @GetMapping("/api/manager_checker/get_reportRecord_studentInfo_honorInfo")
    public List<ReportRecordModel> getReportRecordListFromStudentInfoAndHonorInfo(Long studentId, Long honorId) {
        StudentInfo studentInfo = studentInfoRepository.findStudentInfoById(studentId);
        HonorInfo honorInfo = honorInfoRepository.findHonorInfoById(honorId);
        List<ReportRecordModel> reportRecordModelArrayList = new ArrayList<>();
        List<ReportRecord> reportRecordList = reportRecordRepository.findReportRecordByStudentInfoAndHonorInfo(studentInfo, honorInfo);
        for (int i = 0; i < reportRecordList.size() - 1; i++) {
            ReportRecordModel reportRecordModel = new ReportRecordModel(reportRecordList.get(i));
            reportRecordModelArrayList.add(reportRecordModel);
        }
        return reportRecordModelArrayList;
    }

    @PostMapping("/api/student/add_reportRecord")
    public String addReportRecord(@RequestBody HonorInfoModel honorInfoModel, HttpServletRequest request) {
        HttpSession session = request.getSession();
        StudentInfo studentInfo = studentInfoRepository
                .findStudentInfoByNumber((String) session.getAttribute("userName"));
        HonorInfo honorInfo = new HonorInfo(honorInfoModel);
        ReportRecord reportRecord = new ReportRecord(honorInfo, studentInfo);
        reportRecordRepository.save(reportRecord);
        return "{\"message\":\"add reportRecord success\"}";
    }

    @PostMapping("/api/manager_checker/update_reportRecord")
    public String updateReportRecord(@RequestBody ReportRecordModel reportRecordModel) {
        ReportRecord reportRecord1 = reportRecordRepository.findReportRecordById(reportRecordModel.getId());
        if (reportRecord1 == null) {
            return "{\"message\":\"reportRecord not find\"}";
        } else {
            reportRecord1.setStatus(reportRecordModel.getStatus());
            reportRecordRepository.save(reportRecord1);
        }
        return "{\"message\":\"update reportRecord success\"}";
    }

    @PostMapping("/api/checker/check_reportRecord")
    public String checkReportRecord(@RequestBody ReportRecordModel reportRecordModel
            , HttpServletRequest request) {
        ReportRecord reportRecord1 = reportRecordRepository.findReportRecordById(reportRecordModel.getId());
        if (reportRecord1 == null) {
            return "{\"message\":\"reportRecord not find\"}";
        } else {
            if (reportRecordModel.getStatus().equals("PASS")) {
                HttpSession session = request.getSession();
                CheckerInfo checkerInfo = checkerInfoRepository
                        .findCheckerInfoByUsername((String) session.getAttribute("userName"));
                if (checkerInfo.getAuthority() == CheckerInfo.Authority.FIRST_LEVEL) {
                    reportRecord1.setStatus(ReportRecord.Status.FIRST_REVIEW);
                } else if (checkerInfo.getAuthority() == CheckerInfo.Authority.SECOND_LEVEL) {
                    reportRecord1.setStatus(ReportRecord.Status.ALREADY_REVIEW);
                }

            }else if(reportRecordModel.getStatus().equals("NOT_PASS")){
                reportRecord1.setStatus(ReportRecord.Status.NOT_PASS);
            }
            reportRecordRepository.save(reportRecord1);
            return "{\"message\":\"check reportRecord success\"}";
        }
    }
}
