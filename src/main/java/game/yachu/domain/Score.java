package game.yachu.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Score {
    private List<Category> categories = new ArrayList<>();
    private boolean homework;

    public Score() {
        Genealogy[] genealogies = Genealogy.values();
        for (Genealogy genealogy : genealogies) {
            if (genealogy == Genealogy.SUBTOTAL || genealogy == Genealogy.BONUS || genealogy == Genealogy.TOTAL) {
                categories.add(new Category(genealogy, 0, true));
                continue;
            }
            categories.add(new Category(genealogy, 0, false));
        }
    }

    public Score(List<Integer> points) {
        for (int i = 0; i < points.size(); i++) {
            categories.add(new Category(Genealogy.values()[i], points.get(i), false));
        }
    }

    // 플레이어가 이미 획득한 점수임을 표시
    public void hasGained(Score score /* playerScore */) {
        for (int i = 0; i < categories.size(); i++) {
            Category rankCategory = this.categories.get(i); // 족보를 계산한 score를 기록한 category
            Category playerCategory = score.categories.get(i); // player의 score를 기록한 category
            if (!playerCategory.isAcquired()) {
                // 플레이어가 획득한 점수는 css를 바꾸면 안되고, 획득하지 않은 점수는 회색으로 칠해줘야 하는데...
                rankCategory.acquire(); // boolean 값이 true면 css 바꾸지 마!!
            }
        }
    }

    public boolean isFull() {
        for (Category category : categories) {
            if (!category.isAcquired()) {
                return false;
            }
        }
        return true;
    }

    public void gainPoint(Genealogy genealogy, Score gained) {
        int point = gained.categories.get(genealogy.getIndex()).getPoint();

        if (isHomeworkRange(genealogy)) {
            Category subTotal = categories.get(Genealogy.SUBTOTAL.getIndex());
            subTotal.gain(point);
            gainBonus(subTotal);
        }
        Category category = categories.get(genealogy.getIndex());
        category.gain(point);

        Category total = categories.get(Genealogy.TOTAL.getIndex());
        total.gain(point);
    }

    private void gainBonus(Category subTotal) {
        if (!homework && subTotal.getPoint() >= 63) {
            Category bonus = categories.get(Genealogy.BONUS.getIndex());
            bonus.gain(35);

            homework = true;

            Category total = categories.get(Genealogy.TOTAL.getIndex());
            total.gain(35);
        }
    }

    private boolean isHomeworkRange(Genealogy genealogy) {
        return genealogy.getIndex() >= 0 && genealogy.getIndex() <= 5;
    }
}
