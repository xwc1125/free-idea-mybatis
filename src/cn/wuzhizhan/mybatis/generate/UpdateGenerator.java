package cn.wuzhizhan.mybatis.generate;

import cn.wuzhizhan.mybatis.dom.model.Mapper;
import com.intellij.psi.PsiMethod;
import cn.wuzhizhan.mybatis.dom.model.GroupTwo;

import org.jetbrains.annotations.NotNull;

/**
 * @author yanglin
 */
public class UpdateGenerator extends StatementGenerator {

    public UpdateGenerator(@NotNull String... patterns) {
        super(patterns);
    }

    @NotNull
    @Override
    protected GroupTwo getTarget(@NotNull Mapper mapper, @NotNull PsiMethod method) {
        return mapper.addUpdate();
    }

    @NotNull
    @Override
    public String getId() {
        return "UpdateGenerator";
    }

    @NotNull
    @Override
    public String getDisplayText() {
        return "Update Statement";
    }

}
