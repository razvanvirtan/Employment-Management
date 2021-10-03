package com.tema;

public class Recruiter extends Employee implements Comparable<Recruiter>{
    private double rating;

    Recruiter(Resume resume, long salary) {
        super(resume, salary);
        rating = 5;
    }

    public int evaluate(Job job, User user) {
        double score;

        score = rating * user.getTotalScore();
        rating += 0.1;

        if (job.meetsRequirements(user)) {
            Request<Job, Consumer> newRequest = new Request<Job, Consumer>(job, user,
                    this, score);
            Application application = Application.getInstance();
            application.getCompany(this.getCompany()).getManager().add(newRequest);
        }
        return (int) score;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public int compareTo(Recruiter o) {
        return (int) (o.getRating() - this.getRating()) * 10;
    }
}
