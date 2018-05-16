package com.wugengkj.dance.utils;

/**
 * Created by zwl on 2018/5/12.
 * May god bless me
 */


import com.wugengkj.dance.common.constants.GlobalConstants;
import com.wugengkj.dance.common.enums.TicketType;
import com.wugengkj.dance.entity.Subject;
import com.wugengkj.dance.entity.Ticket;
import com.wugengkj.dance.service.ISubjectService;
import com.wugengkj.dance.service.ITicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * @author zwl
 */
@Slf4j
@Component
public class SubjectUtil {
    @Autowired
    private ISubjectService iSubjectService;
    @Autowired
    private ITicketService iTicketService;

    /**
     * 计算用户得票类型
     * <p>
     * 一共答对3道题以上6道题以下获得看台两侧的票
     * 一共答对6道题以上9道题以下获得看台中央的票
     * 一共答对9道题(全部题都答对了)获得VIP内场的票
     *
     * @param subjectSuccessNum 正确题目数
     * @return 得票类型
     */
    public int ticketId(int subjectSuccessNum) {
        List<Ticket> tickets = iTicketService.queryList();
        Map<Integer, Ticket> maps = new HashMap<>();
        for (Ticket ticket : tickets) {
            maps.put(ticket.getId().intValue(), ticket);
        }
        if (subjectSuccessNum >= GlobalConstants.SUBJECT_NUM_BORDER_FIRST) {
            if (subjectSuccessNum < GlobalConstants.SUBJECT_NUM_BORDER_SECOND) {
                //普通票
                return maps.get(TicketType.ORDINARY.getCode()).getCurrentNum() > 0 ? TicketType.ORDINARY.getCode() : 0;
            }
            if (subjectSuccessNum < GlobalConstants.SUBJECT_NUM_BORDER_THIRD) {
                //看台票
                return maps.get(TicketType.MEDIUM.getCode()).getCurrentNum() > 0 ? TicketType.MEDIUM.getCode() : 0;
            }
            if (subjectSuccessNum == GlobalConstants.SUBJECT_NUM_BORDER_THIRD) {
                //VIP票
                return maps.get(TicketType.SENIOR.getCode()).getCurrentNum() > 0 ? TicketType.SENIOR.getCode() : 0;
            }

        }
        //再接再厉票
        return 0;
    }

    /**
     * 生成随机题目编号
     * 一共九道题
     * 分别为3 3 3
     *
     * @return
     */
    public List<Subject> randomSubject() {
        ArrayList<Subject> list = new ArrayList<>();

        ArrayList<Subject> easyList = iSubjectService.queryEasyList();
        ArrayList<Subject> mediumList = iSubjectService.queryMediumList();
        ArrayList<Subject> hardList = iSubjectService.queryHardList();
        list.addAll(getRandomList(easyList, 3));
        list.addAll(getRandomList(mediumList, 3));
        list.addAll(getRandomList(hardList, 3));


        return list;
    }

    public ArrayList<Subject> copySubjectList(List<Subject> subjects) {
        ArrayList<Subject> listRet = new ArrayList<>();
        subjects.stream().forEach(subject -> {
            Subject s = new Subject();
            BeanUtils.copyProperties(subject, s);
            listRet.add(s);
        });
        return listRet;

    }

    /**
     * @param paramList:被抽取list
     * @param count:抽取元素的个数
     * @function:从list中随机抽取若干不重复元素
     * @return:由抽取元素组成的新list
     */
    private List getRandomList(List paramList, int count) {
        if (paramList.size() < count) {
            return paramList;
        }
        Random random = new Random();
        List<Integer> tempList = new ArrayList<>();
        List<Object> newList = new ArrayList<>();
        int temp = 0;
        for (int i = 0; i < count; i++) {
            //将产生的随机数作为被抽list的索引
            temp = random.nextInt(paramList.size());
            if (!tempList.contains(temp)) {
                tempList.add(temp);
                newList.add(paramList.get(temp));
            } else {
                i--;
            }
        }
        return newList;
    }

}
