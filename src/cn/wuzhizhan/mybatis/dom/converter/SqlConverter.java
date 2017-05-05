package cn.wuzhizhan.mybatis.dom.converter;

import cn.wuzhizhan.mybatis.dom.model.IdDomElement;
import cn.wuzhizhan.mybatis.dom.model.Mapper;
import com.intellij.util.xml.ConvertContext;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * @author yanglin
 */
public class SqlConverter extends IdBasedTagConverter {

    @NotNull
    @Override
    public Collection<? extends IdDomElement> getComparisons(@Nullable Mapper mapper, ConvertContext context) {
        return mapper.getSqls();
    }

}
