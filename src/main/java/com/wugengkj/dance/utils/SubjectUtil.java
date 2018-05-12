package com.wugengkj.dance.utils;

/**
 * Created by zwl on 2018/5/12.
 * May god bless me
 */


import com.wugengkj.dance.common.enums.ticketType;
import com.wugengkj.dance.entity.Subject;
import com.wugengkj.dance.service.ISubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Slf4j
@Component
public class SubjectUtil {
    @Autowired
    private ISubjectService iSubjectService;


    /**
     * 计算用户得票类型
     * <p>
     * 一共答对3道题以上6道题以下获得看台两侧的票
     * 一共答对6道题以上9道题以下获得看台中央的票
     * 一共答对9道题(全部题都答对了)获得VIP内场的票
     *
     * @param subjectSuccessNum 正确题目数
     * @param subjectTotal      用户答题数目
     * @param ticketTotal       票总类型数
     * @return 得票类型
     */
    public int ticketId(int subjectSuccessNum, int subjectTotal, int ticketTotal) {
        if (subjectSuccessNum>=3) {
            if (subjectSuccessNum<6){
                 return ticketType.ORDINARY.getCode();
            }
            if (subjectSuccessNum>=6){
                if (subjectSuccessNum<9){
                    return ticketType.MEDIUM.getCode();
                }
                if (subjectSuccessNum==9){
                    return  ticketType._NONE.getCode();
                }
            }

        }
        return -1;
    }

    /**
     * 生成随机题目编号
     * 一共九道题
     * 分别为3 3 3
     *
     * @return
     */
    public List<Subject> randomSubject() {
        List<Subject> list = new ArrayList<>();
        ArrayList<Subject> easyList = iSubjectService.queryEasyList(-1);
        ArrayList<Subject> mediumList = iSubjectService.queryMediumList(-1);
        ArrayList<Subject> hardList = iSubjectService.queryHardList(-1);
        list.addAll(getRandomList(easyList,3));
        list.addAll(getRandomList(mediumList,3));
        list.addAll(getRandomList(hardList,3));

        return list;
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
            temp = random.nextInt(paramList.size());//将产生的随机数作为被抽list的索引
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
