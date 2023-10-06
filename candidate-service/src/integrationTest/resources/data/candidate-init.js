db = db.getSiblingDB("candidate")
db.createUser({
    user: "candidate_user",
    pwd: "candidate_password",
    roles: [
        {
            role: "readWrite",
            db: "candidate"
        }
    ]
});
db.createCollection("candidates");
db.candidates.insertMany([
    {
        _id: "1",
        name: "John Mayer",
        skills: ["java", "spring"]
    },
    {
        _id: "2",
        name: "Oliver Blane",
        skills: ["docker"]
    },
    {
        _id: "3",
        name: "Diego Garcia",
        skills: ["agile", "jira"]
    },
    {
        name: "Sara Chase",
        skills: ["java", "jira", "project"]
    }
]);