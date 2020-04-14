package org.csu.ouostore.model.vo;

import lombok.Getter;
import lombok.Setter;
import org.csu.ouostore.model.entity.UmsMenu;

import java.util.List;

/**
 * @description: 树形结构menu
 * @author: Guanyu-Cai
 * @Date: 04/12/2020
 */
@Getter
@Setter
public class UmsMenuNodeVo extends UmsMenu {

    private List<UmsMenuNodeVo> children;
}
