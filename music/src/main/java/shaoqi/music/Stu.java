package shaoqi.music;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hayden on 18-6-28.
 */

public class Stu implements Parcelable {

    private String name;
    private int age;

    protected Stu(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<Stu> CREATOR = new Creator<Stu>() {
        @Override
        public Stu createFromParcel(Parcel in) {
            return new Stu(in);
        }

        @Override
        public Stu[] newArray(int size) {
            return new Stu[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
}
