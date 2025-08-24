import { Card, CardBody, CardHeader } from "@nextui-org/react"
import { FileText, Calendar, Shield, AlertTriangle } from "lucide-react"
import NavigationBar from "@/components/Navbar"

export default function TermsPage() {
  const lastUpdated = "August 25, 2025"

  const sections = [
    {
      title: "1. Acceptance of Terms",
      content: [
        "By accessing and using the Errand GO platform, you accept and agree to be bound by the terms and provision of this agreement.",
        "If you do not agree to abide by the above, please do not use this service.",
        "These terms apply to all users of the platform, including customers, errand boys, and administrators.",
      ],
    },
    {
      title: "2. Use License",
      content: [
        "Permission is granted to temporarily download one copy of Errand GO materials for personal, non-commercial transitory viewing only.",
        "This is the grant of a license, not a transfer of title, and under this license you may not:",
        "• Modify or copy the materials",
        "• Use the materials for any commercial purpose or for any public display",
        "• Attempt to reverse engineer any software contained on the platform",
        "• Remove any copyright or other proprietary notations from the materials",
      ],
    },
    {
      title: "3. User Accounts",
      content: [
        "Users must provide accurate and complete information when creating an account.",
        "Users are responsible for maintaining the confidentiality of their account credentials.",
        "Users must notify us immediately of any unauthorized use of their account.",
        "We reserve the right to suspend or terminate accounts that violate these terms.",
        "Users must be at least 18 years old to create an account.",
      ],
    },
    {
      title: "4. Platform Services",
      content: [
        "Errand GO provides a platform connecting customers with service providers (errand boys).",
        "We do not directly provide errand services but facilitate connections between users.",
        "All tasks and services are performed by independent contractors, not Errand GO employees.",
        "We reserve the right to modify or discontinue services at any time.",
      ],
    },
    {
      title: "5. Payment Terms",
      content: [
        "Customers agree to pay the agreed-upon amount for completed tasks.",
        "Payments are processed through our secure payment system.",
        "Service fees and commissions are clearly disclosed before transaction completion.",
        "Refunds are subject to our refund policy and dispute resolution process.",
        "All prices are in USD unless otherwise specified.",
      ],
    },
    {
      title: "6. User Conduct",
      content: [
        "Users must not use the platform for any unlawful or prohibited activities.",
        "Harassment, discrimination, or abusive behavior is strictly prohibited.",
        "Users must not post false, misleading, or fraudulent information.",
        "Spam, solicitation, or unauthorized advertising is not permitted.",
        "Users must respect intellectual property rights of others.",
      ],
    },
    {
      title: "7. Safety and Insurance",
      content: [
        "All errand boys undergo background checks and verification processes.",
        "Tasks are covered by our comprehensive insurance policy.",
        "Users must report safety concerns immediately to our support team.",
        "We maintain the right to investigate and take action on safety violations.",
        "Emergency support is available 24/7 for urgent safety issues.",
      ],
    },
    {
      title: "8. Privacy Policy",
      content: [
        "Your privacy is important to us. Please review our Privacy Policy for details on data collection and use.",
        "We collect only necessary information to provide our services.",
        "Personal information is protected and not shared without consent.",
        "Users can request access to or deletion of their personal data.",
      ],
    },
    {
      title: "9. Limitation of Liability",
      content: [
        "Errand GO shall not be liable for any indirect, incidental, or consequential damages.",
        "Our total liability is limited to the amount paid for the specific service in question.",
        "We are not responsible for the actions or omissions of errand boys or customers.",
        "Users participate in the platform at their own risk.",
      ],
    },
    {
      title: "10. Dispute Resolution",
      content: [
        "Disputes between users should first be resolved through our platform's resolution center.",
        "Unresolved disputes may be subject to binding arbitration.",
        "Legal proceedings must be filed in the jurisdiction where Errand GO is headquartered.",
        "Users waive the right to participate in class action lawsuits.",
      ],
    },
    {
      title: "11. Modifications to Terms",
      content: [
        "We reserve the right to modify these terms at any time.",
        "Users will be notified of significant changes via email or platform notification.",
        "Continued use of the platform after changes constitutes acceptance of new terms.",
        "Users who disagree with changes may terminate their account.",
      ],
    },
    {
      title: "12. Termination",
      content: [
        "Either party may terminate the agreement at any time with proper notice.",
        "We may immediately terminate accounts for violations of these terms.",
        "Upon termination, users must cease all use of the platform.",
        "Certain provisions of these terms survive termination.",
      ],
    },
  ]

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-purple-50">
      <NavigationBar />

      {/* Hero Section */}
      <section className="py-20 px-4">
        <div className="max-w-4xl mx-auto text-center">
          <div className="w-20 h-20 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-6">
            <FileText className="w-10 h-10 text-blue-600" />
          </div>
          <h1 className="text-5xl md:text-6xl font-bold text-gray-900 mb-6">
            Terms of
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-blue-600 to-purple-600"> Service</span>
          </h1>
          <p className="text-xl text-gray-600 mb-8 max-w-2xl mx-auto">
            Please read these terms and conditions carefully before using our service
          </p>
          <div className="flex items-center justify-center gap-2 text-gray-500">
            <Calendar className="w-4 h-4" />
            <span>Last updated: {lastUpdated}</span>
          </div>
        </div>
      </section>

      {/* Important Notice */}
      <section className="py-8 px-4">
        <div className="max-w-4xl mx-auto">
          <Card className="border-l-4 border-yellow-500 bg-yellow-50">
            <CardBody className="p-6">
              <div className="flex items-start gap-3">
                <AlertTriangle className="w-6 h-6 text-yellow-600 flex-shrink-0 mt-0.5" />
                <div>
                  <h3 className="font-semibold text-yellow-800 mb-2">Important Notice</h3>
                  <p className="text-yellow-700">
                    By using Errand GO, you agree to these terms of service. Please read them carefully as they contain
                    important information about your rights and obligations.
                  </p>
                </div>
              </div>
            </CardBody>
          </Card>
        </div>
      </section>

      {/* Terms Content */}
      <section className="py-16 px-4 bg-white">
        <div className="max-w-4xl mx-auto">
          <div className="space-y-8">
            {sections.map((section, index) => (
              <Card key={index}>
                <CardHeader>
                  <h2 className="text-2xl font-semibold text-gray-900">{section.title}</h2>
                </CardHeader>
                <CardBody>
                  <div className="space-y-3">
                    {section.content.map((paragraph, paragraphIndex) => (
                      <p key={paragraphIndex} className="text-gray-700 leading-relaxed">
                        {paragraph}
                      </p>
                    ))}
                  </div>
                </CardBody>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Contact Section */}
      <section className="py-16 px-4">
        <div className="max-w-4xl mx-auto">
          <Card className="bg-gradient-to-r from-blue-600 to-purple-600 text-white">
            <CardBody className="p-8 text-center">
              <Shield className="w-12 h-12 mx-auto mb-4 opacity-90" />
              <h2 className="text-2xl font-bold mb-4">Questions About These Terms?</h2>
              <p className="text-lg opacity-90 mb-6">
                If you have any questions about these Terms of Service, please don't hesitate to contact us.
              </p>
              <div className="space-y-2">
                <p className="font-medium">Email: legal@errandgo.com</p>
                <p className="font-medium">Phone: +254 725 123456</p>
                <p className="opacity-75">Available Monday-Friday, 8AM-5PM EAT</p>
              </div>
            </CardBody>
          </Card>
        </div>
      </section>

      {/* Footer Note */}
      <section className="py-8 px-4 bg-gray-100">
        <div className="max-w-4xl mx-auto text-center">
          <p className="text-gray-600">
            These terms of service are effective as of {lastUpdated} and will remain in effect except with respect to
            any changes in its provisions in the future, which will be in effect immediately after being posted on this
            page.
          </p>
        </div>
      </section>
    </div>
  )
}
